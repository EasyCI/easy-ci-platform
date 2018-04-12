package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.BuildDetail;
import ink.laoliang.easyciplatform.domain.Flow;
import ink.laoliang.easyciplatform.domain.FlowDetail;
import ink.laoliang.easyciplatform.domain.User;
import ink.laoliang.easyciplatform.domain.response.FlowDetailResponse;
import ink.laoliang.easyciplatform.repository.BuildDetailRepository;
import ink.laoliang.easyciplatform.repository.FlowRepository;
import ink.laoliang.easyciplatform.repository.UserRepository;
import ink.laoliang.easyciplatform.util.UserTokenByJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlowRepository flowRepository;

    @Autowired
    private BuildDetailRepository buildDetailRepository;

    @Autowired
    private FlowDetailResponse flowDetailResponse;

    @Override
    public FlowDetailResponse getFlowDetails(String userToken) {
        user = UserTokenByJwt.parserToken(userToken, userRepository);
        List<Flow> flows = flowRepository.findAllByUserEmail(user.getEmail());
        List<FlowDetail> flowDetails = new ArrayList<>();
        Sort orders = new Sort(Sort.Direction.DESC, "queueNumber");
        for (Flow flow : flows) {
            List<BuildDetail> buildDetails = buildDetailRepository.findAllByFlowId(flow.getId(), orders);
            List<BuildDetail> buildSuccessDetails = buildDetailRepository.findAllByFlowIdAndIsSuccess(flow.getId(), true);
            Integer historySum = buildDetails.size();
            Integer successSum = buildSuccessDetails.size();
            Integer failureSum = historySum - successSum;
            Double rating = Double.parseDouble(String.format("%.2f", (double) successSum / (double) historySum * 5));
            String lastBuildTime = buildDetails.get(0).getUpdatedAt().toString().substring(5, 16);
            flowDetails.add(new FlowDetail(flow, historySum, successSum, failureSum, rating, lastBuildTime));
        }
        flowDetailResponse.setFlowDetails(flowDetails);
        return flowDetailResponse;
    }
}
