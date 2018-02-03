package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.response.GitHubTokenResponse;
import ink.laoliang.easyciplatform.exception.GitHubAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@Service
public class GitHubAuthServiceImpl implements GitHubAuthService {

    private final String clientId = "384bd7c1472f8f66807d";
    private final String clientSecret = "8dbb5c28d0bdcac525e0ad13c508442fbb91a3e9";
    private final String scope = "user:mail";

    @Autowired
    private GitHubTokenResponse gitHubTokenResponse;

    @Override
    public RedirectView adk() {
        String getUrl = "https://github.com/login/oauth/authorize?scope=" + scope + "&client_id=" + clientId;
        return new RedirectView(getUrl);
    }

    @Override
    public GitHubTokenResponse callback(String code) {
        String url = "https://github.com/login/oauth/access_token";
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", code);
        HttpEntity httpEntity = new HttpEntity(body, null);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        if (responseEntity.getBody().toString().split("&")[0].split("=")[0].equals("error")) {
            throw new GitHubAuthException(responseEntity.getBody().toString().split("&")[1].split("=")[1]);
        } else {
            gitHubTokenResponse.setStatus("OK");
            gitHubTokenResponse.setAccessToken(responseEntity.getBody().toString().split("&")[0].split("=")[1]);
            return gitHubTokenResponse;
        }
    }
}
