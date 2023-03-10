package com.yunbaek.barogodelivery.delivery.ui;

import com.yunbaek.barogodelivery.delivery.TestUserDetailsService;
import com.yunbaek.barogodelivery.delivery.domain.DeliveryRepository;
import com.yunbaek.barogodelivery.delivery.domain.DeliveryStatus;
import com.yunbaek.barogodelivery.delivery.dto.DeliveryResponse;
import com.yunbaek.barogodelivery.delivery.dto.DeliverySearchDto;
import com.yunbaek.barogodelivery.utils.ControllerTest;
import com.yunbaek.barogodelivery.utils.RestAssertUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("???????????? ")
@Nested
@WebMvcTest(controllers = DeliveryController.class)
@ExtendWith({RestDocumentationExtension.class})
class DeliveryControllerTest extends ControllerTest {

    private static final String DELIVERIES_URI = "/api/v1/deliveries/";

    @MockBean
    private DeliveryRepository deliveryRepository;

    @Autowired
    protected MockMvc mockMvc;

    private final TestUserDetailsService testUserDetailsService = new TestUserDetailsService();

    @BeforeEach()
    public void setup(RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .apply(springSecurity())
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // ??????
                .build();
    }

    @DisplayName("?????? ????????? ????????? ??? ??????.")
    @Test
    void getDeliveryListTest() throws Exception {
        // given
        DeliverySearchDto searchDto = new DeliverySearchDto();
        searchDto.setLastDeliveryId(0L);
        searchDto.setFirstDate(LocalDate.of(2023, 1, 1));
        searchDto.setLastDate(LocalDate.of(2023, 1, 3));
        searchDto.setSize(20);

        given(deliveryService.findDeliveryByMemberId(anyLong(), any(DeliverySearchDto.class)))
                .willReturn(List.of(
                        DeliveryResponse.builder()
                                .deliveryId(1L)
                                .memberId(1L)
                                .riderId(1L)
                                .deliveryStatus(DeliveryStatus.ORDERED)
                                .arrivalZipCode("12345")
                                .arrivalState("?????????")
                                .arrivalCity("?????????")
                                .arrivalStreet("?????????")
                                .arrivalDetail("0??? 123-45")
                                .departureZipCode("12345")
                                .departureState("?????????")
                                .departureCity("?????????")
                                .departureStreet("?????????")
                                .departureDetail("5??? 123-45")
                                .build(),
                        DeliveryResponse.builder()
                                .deliveryId(1L)
                                .memberId(1L)
                                .riderId(1L)
                                .deliveryStatus(DeliveryStatus.ORDERED)
                                .arrivalZipCode("12345")
                                .arrivalState("?????????")
                                .arrivalCity("?????????")
                                .arrivalStreet("?????????")
                                .arrivalDetail("1??? 123-45")
                                .departureZipCode("12345")
                                .departureState("?????????")
                                .departureCity("?????????")
                                .departureStreet("?????????")
                                .departureDetail("5??? 123-45")
                                .build()
                ));

        UserDetails test = testUserDetailsService.loadUserByUsername("Test");

        // when
        ResultActions perform = mockMvc.perform(get(DELIVERIES_URI)
                        .with(user(test))
                        .params(toMultiValueMap(searchDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        perform.andExpect(status().isOk());
        String result = RestAssertUtils.extractResponse(perform);
        assertThat(result).isNotBlank();

        // docs
        createDeliveryListDocs(perform);
    }

    private static void createDeliveryListDocs(ResultActions perform) throws Exception {
        perform.andDo(document("delivery",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParameters(
                        parameterWithName("lastDeliveryId").description("????????? ?????? ????????????"),
                        parameterWithName("firstDate").description("?????? ?????? ??????"),
                        parameterWithName("lastDate").description("?????? ??? ??????"),
                        parameterWithName("size").description("?????? ?????? ?????????").optional()
                ),
                responseFields(
                        fieldWithPath("[].deliveryId").description("?????? ????????????"),
                        fieldWithPath("[].memberId").description("?????? ????????????"),
                        fieldWithPath("[].riderId").description("????????? ????????????"),
                        fieldWithPath("[].deliveryStatus").description("?????? ??????"),
                        fieldWithPath("[].arrivalAddress").description("????????? ??????"),
                        fieldWithPath("[].departureAddress").description("????????? ??????")
                ))
        );
    }

    public MultiValueMap<String, String> toMultiValueMap(DeliverySearchDto searchDto) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("lastDeliveryId", String.valueOf(searchDto.getLastDeliveryId()));
        map.add("firstDate", searchDto.getFirstDate().toString());
        map.add("lastDate", searchDto.getLastDate().toString());
        map.add("size", String.valueOf(searchDto.getSize()));
        return map;
    }

}
