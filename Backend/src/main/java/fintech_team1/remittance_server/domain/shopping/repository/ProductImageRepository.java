package fintech_team1.remittance_server.domain.shopping.repository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductImageRepository {
    // 상품 코드와 이미지 URL을 매핑하는 맵
    private static final Map<String, String> productCodeToImgUrlMap = new HashMap<>();
    static {
        productCodeToImgUrlMap.put("P001", "https://github.com/user-attachments/assets/10034f02-8463-44bb-ab6e-a78e743f973c");
        productCodeToImgUrlMap.put("P002", "https://github.com/user-attachments/assets/829e3afa-a514-4768-88e6-ab0fff7a6609");
        productCodeToImgUrlMap.put("P003", "https://github.com/user-attachments/assets/a1415331-b301-4bde-a031-fbdcd3aaaed3");
        productCodeToImgUrlMap.put("P004", "https://github.com/user-attachments/assets/c94d5208-2815-444a-b454-1014f68a025d");
        productCodeToImgUrlMap.put("P005", "https://github.com/user-attachments/assets/c9b3b94a-c021-40ae-852a-18e0468c853d");
        productCodeToImgUrlMap.put("P006", "https://github.com/user-attachments/assets/606daa2f-05e6-4fb5-9c03-959d138ccd35");
        productCodeToImgUrlMap.put("P007", "https://github.com/user-attachments/assets/dcde34cf-955e-4779-a055-56313c0c86fe");
        productCodeToImgUrlMap.put("P008", "https://github.com/user-attachments/assets/dc5c58ee-f4a1-40e3-bcba-66280a2372ce");
        productCodeToImgUrlMap.put("P009", "https://github.com/user-attachments/assets/f48c2ba5-b6ae-4868-8527-95f590cfceb9");
        productCodeToImgUrlMap.put("P010", "https://github.com/user-attachments/assets/3467ab8d-9bc1-4a13-8a7b-dc88c95031b6");
        productCodeToImgUrlMap.put("P011", "https://github.com/user-attachments/assets/73868258-9e0d-471a-93fc-55e584dfe73c");
        productCodeToImgUrlMap.put("P012", "https://github.com/user-attachments/assets/3ca24e65-7429-49b8-acf9-d3080fc236bf");
        productCodeToImgUrlMap.put("P013", "https://github.com/user-attachments/assets/074c29ff-9053-4c49-9398-3b7ffa4919bd");
        productCodeToImgUrlMap.put("P014", "https://github.com/user-attachments/assets/4d921d37-2351-41cb-a971-c0f16d08cd3a");
        productCodeToImgUrlMap.put("P015", "https://github.com/user-attachments/assets/081d3c1f-56ec-4529-b33e-7e23c58daf64");
        productCodeToImgUrlMap.put("P016", "https://github.com/user-attachments/assets/c3222526-86a0-4ef4-81af-b04392dcf0f2");
        productCodeToImgUrlMap.put("P017", "https://github.com/user-attachments/assets/fea082f1-70f6-48f3-9df6-86f9456d4418");
        productCodeToImgUrlMap.put("P018", "https://github.com/user-attachments/assets/6044e23e-1574-43fe-9bc6-34f7e02338fb");
        productCodeToImgUrlMap.put("P019", "https://github.com/user-attachments/assets/3b9df62e-23bf-4397-b814-c93e754d2407");
        productCodeToImgUrlMap.put("P020", "https://github.com/user-attachments/assets/a469b035-083f-49a6-b3b4-5b72f9768e1d");
        productCodeToImgUrlMap.put("P021", "https://github.com/user-attachments/assets/c4c37910-8af4-4e66-a1d5-232ae2b7c878");
        productCodeToImgUrlMap.put("P022", "https://github.com/user-attachments/assets/4d43e205-0ad3-41c9-9986-cb700e235a81");
        productCodeToImgUrlMap.put("P023", "https://github.com/user-attachments/assets/97165d0d-2f7c-4591-9c19-fc589158a34f");
        productCodeToImgUrlMap.put("P024", "https://github.com/user-attachments/assets/a033b1ab-f9cc-46c3-945f-d6f7064404f3");
        productCodeToImgUrlMap.put("P025", "https://github.com/user-attachments/assets/aeb6c412-0510-4aea-ab53-601289cb9b6e");
        productCodeToImgUrlMap.put("P026", "https://github.com/user-attachments/assets/3acc10b7-4da8-475e-8014-ee7f2c015a1c");
        productCodeToImgUrlMap.put("P027", "https://github.com/user-attachments/assets/6504af19-a56c-415f-adf9-69fc7a569fa3");
        productCodeToImgUrlMap.put("P028", "https://github.com/user-attachments/assets/6e5c4103-832f-4437-87c6-c5b09de646b7");
        productCodeToImgUrlMap.put("P029", "https://github.com/user-attachments/assets/7073a568-cfba-4f13-8ac5-cbb9881b70f7");
        productCodeToImgUrlMap.put("P030", "https://github.com/user-attachments/assets/05f83c0f-88d5-415d-a5b1-6c1d64a362ab");
    }


    // 상품 코드로 이미지 URL을 조회하는 메서드
    public String selectImgUrlByCode(String productCode) {
        String url = productCodeToImgUrlMap.get(productCode);
        if (url == null) {
            throw new IllegalArgumentException("유효하지 않은 상품 코드입니다.");
        }
        return url;
    }

    // 모든 이미지 URL을 반환하는 메서드
    public List<String> findByAllImgUrls() {
        return List.copyOf(productCodeToImgUrlMap.values());
    }
}
