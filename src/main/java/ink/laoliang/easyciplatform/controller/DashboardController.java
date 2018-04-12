package ink.laoliang.easyciplatform.controller;

import ink.laoliang.easyciplatform.domain.response.FlowDetailResponse;
import ink.laoliang.easyciplatform.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping(value = "get_flow_details")
    public FlowDetailResponse getFlowDetails(@RequestHeader(value = "Authorization") String userToken) {
        return dashboardService.getFlowDetails(userToken);
    }
}
