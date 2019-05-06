package org.zdd.bookmall.web.controller.admin;

import com.github.pagehelper.PageInfo;
import org.zdd.bookmall.model.dao.BookDescMapper;
import org.zdd.bookmall.common.utils.IDUtils;
import org.zdd.bookmall.exception.BSException;
import org.zdd.bookmall.model.entity.BookDesc;
import org.zdd.bookmall.model.entity.BookInfo;
import org.zdd.bookmall.model.entity.Store;
import org.zdd.bookmall.model.service.IBookInfoService;
import org.zdd.bookmall.model.service.IStoreService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin/book")
@RequiresPermissions("book-manage")
public class AdminBookController {

    @Autowired
    private IBookInfoService bookInfoService;

    @Autowired
    private BookDescMapper bookDescMapper;

    @Autowired
    private IStoreService storeService;

    @Value("${image.url.prefix}")
    private String urlPrefix;

    @RequestMapping("toAddition")
    @RequiresPermissions("book-add")
    public String toAddition(){
        return "admin/book/add";
    }

    @RequestMapping("/addition")
    @RequiresPermissions("book-add")
    public String addBook(BookInfo bookInfo, String bookDesc, MultipartFile pictureFile, HttpServletRequest request) throws Exception {

        uploadPicture(bookInfo, pictureFile, request);
        bookInfoService.saveBook(bookInfo, bookDesc);

        return "redirect:/admin/book/list";
    }

    @RequestMapping(value = "/list")
    @RequiresPermissions("book-query")
    public String bookList(@RequestParam(defaultValue = "", required = false) String keywords,
                          @RequestParam(value = "page",defaultValue = "1",required = false) int page,
                          HttpSession session,
                          Model model) {
        keywords = keywords.trim();
        Store store = (Store)session.getAttribute("loginStore");

        if(store != null){
            PageInfo<BookInfo> books = bookInfoService.findBookListByCondition(keywords,0,page,10,store.getStoreId());
            model.addAttribute("bookPageInfo", books);
            model.addAttribute("keywords", keywords);
        }else {
            model.addAttribute("exception", "您请求的资源不存在");
            return "exception";
        }

        return "admin/book/list";
    }

    /**
     * 更新页面回显
     * @param bookId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/echo")
    @RequiresPermissions("book-edit")
    public String echo(int bookId,Model model) throws BSException {

        BookInfo bookInfo = bookInfoService.adminFindById(bookId);

        BookDesc bookDesc = bookDescMapper.selectByPrimaryKey(bookInfo.getBookId());

        model.addAttribute("bookInfo", bookInfo);

        model.addAttribute("bookDesc", bookDesc);

        return "admin/book/edit";
    }

    @RequestMapping("/update")
    @RequiresPermissions("book-edit")
    public String updateBook(BookInfo bookInfo, String bookDesc, String keywords,MultipartFile pictureFile, HttpServletRequest request,RedirectAttributes ra) throws Exception {

        uploadPicture(bookInfo, pictureFile, request);
        bookInfoService.updateBook(bookInfo, bookDesc);
        ra.addAttribute("keywords", keywords);
        return "redirect:/admin/book/list";
    }

    @RequestMapping("/deletion/{bookId}")
    @RequiresPermissions("book-delete")
    public String deletion(@PathVariable("bookId") int bookId, String keywords, RedirectAttributes ra){
        bookInfoService.deleteBook(bookId);
        ra.addAttribute("keywords", keywords);
        return "redirect:/admin/book/list";
    }

    @RequestMapping("/shelf")
    @RequiresPermissions("book-shelf")
    public String bookOffShelf(int bookId, int isShelf, String keywords,RedirectAttributes ra){

        bookInfoService.changeShelfStatus(bookId,isShelf);
        ra.addAttribute("keywords", keywords);
        return "redirect:/admin/book/list";
    }

    private void uploadPicture(BookInfo bookInfo, MultipartFile pictureFile, HttpServletRequest request) throws IOException {
        if(pictureFile != null){
            if (StringUtils.isNotBlank(pictureFile.getOriginalFilename())) {
                String realPath = request.getSession().getServletContext().getRealPath("/" + urlPrefix);
                //原始文件名称
                String pictureFileName = pictureFile.getOriginalFilename();
                //新文件名称
                String newFileName = IDUtils.genShortUUID() + pictureFileName.substring(pictureFileName.lastIndexOf("."));

                //上传图片
                File uploadPic = new File(realPath + "/" + newFileName);

                //向磁盘写文件
                pictureFile.transferTo(uploadPic);
                bookInfo.setImageUrl(urlPrefix + "/" + newFileName);
            }
        }
    }

}
