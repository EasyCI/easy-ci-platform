package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.exception.GithubAuthException;
import ink.laoliang.easyciplatform.util.CustomConfigration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CallbackServiceImpl implements CallbackService {

    @Autowired
    private CustomConfigration customConfigration;

    @Autowired
    private ReposService reposService;

    @Override
    public String callback(String code, String userToken) {
        String url = "https://github.com/login/oauth/access_token";
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", customConfigration.getGithubClientId());
        body.add("client_secret", customConfigration.getGithubClientSecret());
        body.add("code", code);
        HttpEntity httpEntity = new HttpEntity(body, null);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        if (responseEntity.getBody().toString().split("&")[0].split("=")[0].equals("error")) {
            throw new GithubAuthException(responseEntity.getBody().toString().split("&")[1].split("=")[1]);
        } else {
            String accessToken = responseEntity.getBody().toString().split("&")[0].split("=")[1];
            reposService.updateGithubAccount(userToken, accessToken);
            return "github-auth-success";
        }
    }
}
