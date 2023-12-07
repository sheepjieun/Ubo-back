package com.coconut.ubo.config;

import com.coconut.ubo.domain.user.College;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.domain.user.UserStatus;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.coconut.ubo.domain.user.UserStatus.ACTIVE;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct //의존성 주입이 완료된 후 실행될 메소드를 지정. 스프링 빈이 생성된 직후 한 번만 호출
    public void init() {
        initService.dbInit(); //데이터베이스 초기화 작업 수행
    }

    @Component //InitService 클래스를 스프링 컴포넌트로 선언. 스프링이 관리하는 빈으로 등록
    @Transactional //이 클래스의 메소드들이 트랜잭션 범위 안에서 실행되도록 지정
    @RequiredArgsConstructor //Lombok이 필요한 생성자를 자동으로 생성. final 필드에 대한 생성자를 자동으로 만듦.
    static class InitService {

        private final EntityManager em;

        public void dbInit() {

            //학교
//            College college1 = new College("명지전문대학교");
//            College college2 = new College("고려대학교");
//            College college3 = new College("연세대학교");
//
//            em.persist(college1);
//            em.persist(college2);
//            em.persist(college3);
//
//            //User
//            User user1 = User.builder().loginId("sheepjieun").email("sheepjieun@example.com").password("1234").nickname("꼬됴").college(college1).status(ACTIVE).build();
//            User user2 = User.builder().loginId("janoho").email("janojo@example.com").password("1234").nickname("양지은").college(college2).status(ACTIVE).build();
//
//            em.persist(user1);
//            em.persist(user2);
//
//            // UsedItem - 서적 5개, 실습도구 4개, 부동산 5개, 삽니다 3개, 기타 6개 생성
//            createUsedItems(5, Category.BOOK, user1);
//            createUsedItems(4, Category.TOOL, user1);
//            createUsedItems(5, Category.HOUSE, user2);
//            createUsedItems(3, Category.BUY, user2);
//            createUsedItems(6, Category.ETC, user1);
//
//            // RentalItem 7개 생성
//            createRentalItems(7, user2);
        }

//        private void createUsedItems(int count, Category category, User seller) {
//            for (int i = 1; i <= count; i++) {
//                UsedItem usedItem = UsedItem.builder()
//                        .title(category.name() + " 물품 " + i)
//                        .price(10000 * i)
//                        .description("설명 " + i)
//                        .major("컴퓨터공학과")
//                        .category(category)
//                        .itemStatus(ItemStatus.TRADE_AVAIL)
//                        .seller(seller)
//                        .build();
//                em.persist(usedItem);
//                createImageSetAndDetails(usedItem, 2); // 각 UsedItem에 대해 2개의 이미지 세트 생성
//            }
//        }
//
//        private void createRentalItems(int count, User seller) {
//            for (int i = 1; i <= count; i++) {
//                RentalItem rentalItem = RentalItem.builder()
//                        .title("대여 물품 " + i)
//                        .deposit(5000 * i)
//                        .price(1000 * i)
//                        .description("대여 물품 설명 " + i)
//                        .major("컴퓨터공학과")
//                        .itemStatus(ItemStatus.RENT_AVAIL)
//                        .seller(seller)
//                        .build();
//                em.persist(rentalItem);
//                createImageSetAndDetails(rentalItem, 3); // 각 RentalItem에 대해 3개의 이미지 세트 생성
//            }
//        }
//
//        private void createImageSetAndDetails(Item item, int imageCount) {
//            ImageSet imageSet = new ImageSet(item);
//            em.persist(imageSet);
//
//            for (int i = 1; i <= imageCount; i++) {
//                ImageDetail imageDetail = ImageDetail.builder()
//                        .imageSet(imageSet)
//                        .fileName("image_" + i + ".jpg")
//                        .fileUrl("http://example.com/image_" + i + ".jpg")
//                        .fileOrder(i)
//                        .build();
//                imageSet.getImageDetails().add(imageDetail);
//                em.persist(imageDetail);
//            }
//        }
    }
}



