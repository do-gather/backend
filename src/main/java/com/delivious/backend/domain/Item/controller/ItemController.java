package com.delivious.backend.domain.Item.controller;



import com.delivious.backend.domain.config.auth.PrincipalDetails;
import com.delivious.backend.domain.cart.entity.Cart;
import com.delivious.backend.domain.cart.entity.CartItem;
import com.delivious.backend.domain.Item.entity.Item;
import com.delivious.backend.domain.user.User;
import com.delivious.backend.domain.cart.CartService;
import com.delivious.backend.domain.Item.ItemService;
import com.delivious.backend.domain.user.UserPageService;




import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final UserPageService userPageService;
    private final CartService cartService;

    // 메인 페이지 html 하나로 통일
    // 메인 페이지 (로그인 안 한 유저) /localhost:8080
    @GetMapping("/")
    public String mainPageNoneLogin(Model model) {
        // 로그인을 안 한 경우
        List<Item> items = itemService.allItemView();
        model.addAttribute("items", items);

        return "main";
    }

    // 메인 페이지 (로그인 유저) - 판매자, 구매자 로 로그인
    @GetMapping("/main")
    public String mainPage(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
            // 판매자
            int sellerId = principalDetails.getUser().getId();
            List<Item> items = itemService.allItemView();
            model.addAttribute("items", items);
            model.addAttribute("user", userPageService.findUser(sellerId));

            return "/main";
        } else {
            // 구매자
            int userId = principalDetails.getUser().getId();
            List<Item> items = itemService.allItemView();
            model.addAttribute("items", items);
            model.addAttribute("user", userPageService.findUser(userId));

            return "/main";
        }
    }

    // 상품 등록 페이지 - 판매자만 가능
    @GetMapping("/item/new")
    public String itemSaveForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if(principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
            // 판매자
            model.addAttribute("user", principalDetails.getUser());

            return "/seller/itemForm";
        } else {
            // 일반 회원이면 거절 -> main
            return "redirect:/main";
        }
    }

    // 상품 등록 (POST) - 판매자만 가능
    @PostMapping("/item/new/pro")
    public String itemSave(Item item, @AuthenticationPrincipal PrincipalDetails principalDetails, MultipartFile imgFile) throws Exception {
        if(principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
            // 판매자
            item.setSeller(principalDetails.getUser());
            itemService.saveItem(item, imgFile);

            return "redirect:/main";
        } else {
            // 일반 회원이면 거절 -> main
            return "redirect:/main";
        }
    }

    // 상품 수정 페이지 - 판매자만 가능
    @GetMapping("/item/modify/{id}")
    public String itemModifyForm(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
            // 판매자
            User user = itemService.itemView(id).getSeller();
            // 상품을 올린 판매자 id와 현재 로그인 중인 판매자의 id가 같아야 수정 가능
            if(user.getId() == principalDetails.getUser().getId()) {

                model.addAttribute("item", itemService.itemView(id));
                model.addAttribute("user", principalDetails.getUser());

                return "/seller/itemModify";
            } else {
                return "redirect:/main";
            }
        } else {
            // 일반 회원이면 거절 -> main
            return "redirect:/main";
        }
    }

    // 상품 수정 (POST) - 판매자만 가능
    @PostMapping("/item/modify/pro/{id}")
    public String itemModify(Item item, @PathVariable("id") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails, MultipartFile imgFile) throws Exception{
        if(principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
            // 판매자
            User user = itemService.itemView(id).getSeller();

            if(user.getId() == principalDetails.getUser().getId()) {
                // 상품을 올린 판매자 id와 현재 로그인 중인 판매자의 id가 같아야 수정 가능
                itemService.itemModify(item, id, imgFile);

                return "redirect:/main";
            } else {
                return "redirect:/main";
            }
        } else {
            // 일반 회원이면 거절 -> main
            return "redirect:/main";
        }
    }

    // 상품 상세 페이지 - 판매자, 구매자 가능
    @GetMapping("/item/view/{itemId}")
    public String ItemView(Model model, @PathVariable("itemId") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
            // 판매자
            User user = principalDetails.getUser();

            model.addAttribute("item", itemService.itemView(id));
            model.addAttribute("user", user);

            return "itemView";
        } else {
            // 구매자
            User user = principalDetails.getUser();

            // 페이지에 접속한 유저를 찾아야 함
            User loginUser = userPageService.findUser(user.getId());

            int cartCount = 0;
            Cart userCart = cartService.findUserCart(loginUser.getId());
            List<CartItem> cartItems = cartService.allUserCartView(userCart);

            for(CartItem cartItem : cartItems) {
                cartCount += cartItem.getCount();
            }

            model.addAttribute("cartCount", cartCount);
            model.addAttribute("item", itemService.itemView(id));
            model.addAttribute("user", user);

            return "itemView";
        }
    }

    // 상품 상세 페이지 - 로그인 안 한 유저
    @GetMapping("/item/view/nonlogin/{id}")
    public String nonLoginItemView(Model model, @PathVariable("id") Integer id) {
        // 로그인 안 한 유저
        model.addAttribute("item", itemService.itemView(id));
        return "itemView";

    }

    // 상품 삭제 - 판매자만 가능
    @GetMapping("/item/delete/{id}")
    public String itemDelete(@PathVariable("id") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
            // 판매자
            User user = itemService.itemView(id).getSeller();

            if(user.getId() == principalDetails.getUser().getId()) {
                // 상품을 올린 판매자 id와 현재 로그인 중인 판매자의 id가 같아야 삭제 가능
                itemService.itemDelete(id);

                return "redirect:/main";
            } else {
                return "redirect:/main";
            }
        } else {
            // 일반 회원이면 거절 -> main
            return "redirect:/main";
        }
    }

    // 상품 리스트 페이지 - 로그인 유저
    @GetMapping("/item/list")
    public String itemList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                           String searchKeyword, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        User user = userPageService.findUser(principalDetails.getUser().getId());

        Page<Item> items = null;

        if (searchKeyword == null) {  // 검색이 들어왔을 때
            items = itemService.allItemViewPage(pageable);
        } else {  // 검색이 들어오지 않았을 때
            items = itemService.itemSearchList(searchKeyword, pageable);
        }

        int nowPage = items.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, items.getTotalPages());

        model.addAttribute("items", items);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("user", user);

        return "itemList";
    }

    // 상품 리스트 페이지 - 로그인 안 한 유저
    @GetMapping("/nonlogin/item/list")
    public String itemList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                           String searchKeyword) {

        Page<Item> items = null;

        if (searchKeyword == null) {  // 검색이 들어왔을 때
            items = itemService.allItemViewPage(pageable);
        } else {  // 검색이 들어오지 않았을 때
            items = itemService.itemSearchList(searchKeyword, pageable);
        }

        int nowPage = items.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, items.getTotalPages());

        model.addAttribute("items", items);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "itemList";
    }
}
