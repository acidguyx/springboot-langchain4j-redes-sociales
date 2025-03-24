package axpen.logics.springbootlangchain4jtiktok1.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class GeminiConfig {

    @Value("${gemini.flash.api-key}")
    private String flashApiKey;

    @Value("${gemini.flash.model-name}")
    private String flashModelName;

    @Value("${gemini.flash.temperature}")
    private Double flashTemperature;

    @Value("${gemini.pro.api-key}")
    private String proApiKey;

    @Value("${gemini.pro.model-name}")
    private String proModelName;

    @Value("${gemini.pro.temperature}")
    private Double proTemperature;

    @Value("${gemini.log-requests}")
    private Boolean logRequests;

    @Bean
    @Primary
    public ChatLanguageModel geminiFlashModel() {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(flashApiKey)
                .modelName(flashModelName)
                .responseFormat(ResponseFormat.JSON)
                .temperature(flashTemperature)
                .logRequestsAndResponses(logRequests)
                .build();
    }

    @Bean
    public ChatLanguageModel geminiProModel() {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(proApiKey)
                .modelName(proModelName)
                .responseFormat(ResponseFormat.JSON)
                .temperature(proTemperature)
                .logRequestsAndResponses(logRequests)
                .build();
    }

}