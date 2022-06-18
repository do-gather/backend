package com.delivious.backend.domain.Item;


import com.delivious.backend.domain.cart.CartService;
import com.delivious.backend.domain.sale.SaleService;
import com.delivious.backend.domain.cart.entity.CartItem;
import com.delivious.backend.domain.Item.entity.Item;
import com.delivious.backend.domain.Item.repository.ItemRepository;
import com.delivious.backend.domain.sale.entity.SaleItem;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CartService cartService;
    private final SaleService saleService;

    // 상품 등록
    public void saveItem(Item item, MultipartFile imgFile) throws Exception {

        String oriImgName = imgFile.getOriginalFilename();
        String imgName = "";

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";

        // UUID 를 이용하여 파일명 새로 생성
        // UUID - 서로 다른 객체들을 구별하기 위한 클래스
        UUID uuid = UUID.randomUUID();

        String savedFileName = uuid + "_" + oriImgName; // 파일명 -> imgName

        imgName = savedFileName;

        File saveFile = new File(projectPath, imgName);

        imgFile.transferTo(saveFile);

        item.setImgName(imgName);

        item.setImgPath("/files/" + imgName);

        itemRepository.save(item);
    }

    // 상품 개별 불러오기
    public Item itemView(Integer id) {
        return itemRepository.findById(id).get();
    }

    // 상품 리스트 불러오기
    public List<Item> allItemView() {
        return itemRepository.findAll();
    }

    // 상품 리스트 페이지용 불러오기
    public Page<Item> allItemViewPage(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    // 상품 수정
    @Transactional
    public void itemModify(Item item, Integer id, MultipartFile imgFile) throws Exception {

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + imgFile.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);
        imgFile.transferTo(saveFile);

        Item update = itemRepository.findItemById(id);
        update.setName(item.getName());
        update.setText(item.getText());
        update.setPrice(item.getPrice());
        update.setStock(item.getStock());
        update.setIsSoldout(item.getIsSoldout());
        update.setImgName(fileName);
        update.setImgPath("/files/"+fileName);
        itemRepository.save(update);
    }

    // 상품 삭제
    @Transactional
    public void itemDelete(Integer id) {
        // cartItem 중에 해당 id 를 가진 item 찾기
        List<CartItem> items = cartService.findCartItemByItemId(id);

        for(CartItem item : items) {
            cartService.cartItemDelete(item.getId());
        }

        itemRepository.deleteById(id);
    }

    // 상품 검색
    public Page<Item> itemSearchList(String searchKeyword, Pageable pageable) {

        return itemRepository.findByNameContaining(searchKeyword, pageable);
    }

}
