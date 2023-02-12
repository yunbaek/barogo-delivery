package com.yunbaek.barogodelivery.delivery.ui;

import com.yunbaek.barogodelivery.delivery.TestUserDetailsService;
import com.yunbaek.barogodelivery.delivery.domain.Address;
import com.yunbaek.barogodelivery.delivery.domain.Delivery;
import com.yunbaek.barogodelivery.delivery.domain.DeliveryRepository;
import com.yunbaek.barogodelivery.delivery.domain.DeliveryStatus;
import com.yunbaek.barogodelivery.delivery.dto.DeliveryResponse;
import com.yunbaek.barogodelivery.delivery.dto.DeliverySearchDto;
import com.yunbaek.barogodelivery.delivery.dto.DeliveryUpdateRequest;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("사용자는 ")
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
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터
                .build();
    }

    @DisplayName("배달 목록을 조회할 수 있다.")
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
                                .arrivalState("서울시")
                                .arrivalCity("강남구")
                                .arrivalStreet("역삼동")
                                .arrivalDetail("0길 123-45")
                                .departureZipCode("12345")
                                .departureState("서울시")
                                .departureCity("강남구")
                                .departureStreet("역삼동")
                                .departureDetail("5길 123-45")
                                .build(),
                        DeliveryResponse.builder()
                                .deliveryId(1L)
                                .memberId(1L)
                                .riderId(1L)
                                .deliveryStatus(DeliveryStatus.ORDERED)
                                .arrivalZipCode("12345")
                                .arrivalState("서울시")
                                .arrivalCity("강남구")
                                .arrivalStreet("역삼동")
                                .arrivalDetail("1길 123-45")
                                .departureZipCode("12345")
                                .departureState("서울시")
                                .departureCity("강남구")
                                .departureStreet("역삼동")
                                .departureDetail("5길 123-45")
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

    @DisplayName("배달 도착 주소를 변경할 수 있다.")
    @Test
    void updateDepartureAddressTest() throws Exception {
        // given
        given(deliveryRepository.findByIdAndMemberId(anyLong(), eq(1L)))
                .willReturn(
                        Optional.of(Delivery.of(
                                1L,
                                1L,
                                1L,
                                Address.of(
                                        "12345",
                                        "서울시",
                                        "강남구",
                                        "역삼동",
                                        "0길 123-45"
                                ),
                                Address.of(

                                        "12345",
                                        "서울시",
                                        "강남구",
                                        "역삼동",
                                        "5길 123-45"
                                )
                        )));

        UserDetails test = testUserDetailsService.loadUserByUsername("Test");
        DeliveryUpdateRequest request = DeliveryUpdateRequest.builder()
                .zipCode("54321")
                .state("서울시")
                .city("송파구")
                .street("잠실동")
                .detail("123길 45-67")
                .build();

        // when
        ResultActions perform = mockMvc.perform(put(DELIVERIES_URI + "/1")
                        .with(user(test))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(RestAssertUtils.mapAsString(request)))
                .andDo(print());

        // then
        perform.andExpect(status().isOk());
        String result = RestAssertUtils.extractResponse(perform);
        assertThat(result).isNotBlank();

        // docs
    }

    private static void createDeliveryListDocs(ResultActions perform) throws Exception {
        perform.andDo(document("delivery",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParameters(
                        parameterWithName("lastDeliveryId").description("마지막 배달 고유번호"),
                        parameterWithName("firstDate").description("검색 시작 날짜"),
                        parameterWithName("lastDate").description("검색 끝 날짜"),
                        parameterWithName("size").description("배달 목록 사이즈").optional()
                ),
                responseFields(
                        fieldWithPath("[].deliveryId").description("배달 고유번호"),
                        fieldWithPath("[].memberId").description("회원 고유번호"),
                        fieldWithPath("[].riderId").description("라이더 고유번호"),
                        fieldWithPath("[].deliveryStatus").description("배달 상태"),
                        fieldWithPath("[].arrivalAddress").description("출발지 주소"),
                        fieldWithPath("[].departureAddress").description("도착지 주소")
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
