package com.yunbaek.barogodelivery.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunbaek.barogodelivery.auth.application.AuthService;
import com.yunbaek.barogodelivery.auth.ui.AuthController;
import com.yunbaek.barogodelivery.delivery.application.DeliveryService;
import com.yunbaek.barogodelivery.delivery.ui.DeliveryController;
import com.yunbaek.barogodelivery.member.application.MemberService;
import com.yunbaek.barogodelivery.member.ui.MemberController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

/**
 * Controller Test를 위한 Abstract Class
 *
 * @author yunbaek
 * <p>
 * 상속 받은 후에 @WebMvcTest에 Controller를 추가해주면 된다.
 * 그 후 @MockBean으로 Mocking할 Service를 추가한다.
 */
@AutoConfigureRestDocs
@WebMvcTest(controllers = {
        MemberController.class,
        AuthController.class,
        DeliveryController.class
})
@ExtendWith({RestDocumentationExtension.class})
@ActiveProfiles("test")
public abstract class ControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected DeliveryService deliveryService;


    @BeforeEach()
    public void setup(RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터
                .build();
    }
}
