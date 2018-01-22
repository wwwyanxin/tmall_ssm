package xin.wyan.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xin.wyan.tmall.pojo.Product;
import xin.wyan.tmall.pojo.ProductImage;
import xin.wyan.tmall.service.ProductImageService;
import xin.wyan.tmall.service.ProductService;
import xin.wyan.tmall.util.ImageType;
import xin.wyan.tmall.util.ImageUtil;
import xin.wyan.tmall.util.UploadedImageFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

@Controller
public class ProductImageController {
    @Autowired
    ProductService productService;

    @Autowired(required = false)
    ProductImageService productImageService;

    @RequestMapping("admin_productImage_add")
    public String add(ProductImage productImage, HttpSession session, UploadedImageFile uploadedImageFile) throws Throwable {
        productImageService.add(productImage);
        String fileName = productImage.getId() + ".jpg";
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;
        ImageType imageType = ImageType.fromType(productImage.getType());
        switch (imageType) {
            case single:
                imageFolder = session.getServletContext().getRealPath("img/productSingle");
                imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
                imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");
                break;
            case detail:
                imageFolder = session.getServletContext().getRealPath("img/productDetail");
                break;
            default:
                throw new Throwable("图片类型异常");
        }
        File file = new File(imageFolder, fileName);
        file.getParentFile().mkdirs();
        try {
            uploadedImageFile.getImage().transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
            if (ImageType.single.equals(imageType)) {
                File file_small = new File(imageFolder_small,fileName);
                File file_middle = new File(imageFolder_middle,fileName);

                ImageUtil.resizeImage(file, 56, 56, file_small);
                ImageUtil.resizeImage(file, 217, 190, file_middle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:admin_productImage_list?pid=" + productImage.getPid();
    }

    @RequestMapping("admin_productImage_delete")
    public String delete(int id, HttpSession session) throws Throwable {
        ProductImage productImage = productImageService.get(id);

        String fileName = productImage.getId() + ".jpg";
        String imageFolder;
        File imageFile;

        ImageType imageType = ImageType.fromType(productImage.getType());
        switch (imageType) {
            case single:
                imageFolder = session.getServletContext().getRealPath("img/productSingle");
                String imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
                String imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");
                imageFile = new File(imageFolder, fileName);
                File file_small = new File(imageFolder_small, fileName);
                File file_middle = new File(imageFolder_middle, fileName);
                file_small.delete();
                file_middle.delete();
                break;
            case detail:
                imageFolder = session.getServletContext().getRealPath("img/productDetail");
                imageFile = new File(imageFolder, fileName);
                break;
            default:
                throw new Throwable("图片类型异常");
        }
        imageFile.delete();
        productImageService.delete(id);
        return "redirect:admin_productImage_list?pid=" + productImage.getPid();
    }

    @RequestMapping("admin_productImage_list")
    public String list(int pid, Model model) {
        Product product = productService.get(pid);
        List<ProductImage> pisSingle = productImageService.list(pid, ImageType.single);
        List<ProductImage> pisDetail = productImageService.list(pid, ImageType.detail);

        model.addAttribute("p", product);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);

        return "admin/listProductImage";
    }
}
