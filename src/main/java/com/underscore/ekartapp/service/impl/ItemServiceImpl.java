/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.service.impl;

import com.amazonaws.util.IOUtils;
import com.underscore.ekartapp.entity.Item;
import com.underscore.ekartapp.entity.ItemImage;
import com.underscore.ekartapp.entity.User;
import com.underscore.ekartapp.exception.NotFoundException;
import com.underscore.ekartapp.form.ItemForm;
import com.underscore.ekartapp.form.ItemUpdateForm;
import com.underscore.ekartapp.form.StatusUpdateForm;
import com.underscore.ekartapp.repository.CategoryRepository;
import com.underscore.ekartapp.repository.ItemImageRepository;
import com.underscore.ekartapp.repository.ItemRepository;
import com.underscore.ekartapp.repository.UserRepository;
import com.underscore.ekartapp.service.ItemService;
import com.underscore.ekartapp.util.SecurityUtil;
import com.underscore.ekartapp.util.storage.Storage;
import com.underscore.ekartapp.view.ItemView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author johnythomas
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemImageRepository itemImageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Value("${manual.upload.folder}")
    private String uploadFolder;
    @Value("${download.url}")
    private String downloadUrl;
    @Autowired
    Storage amazonS3Storage;
    Logger logger = Logger.getLogger(ItemServiceImpl.class.getName());

    @Override
    @Transactional
    public ItemView addItem(ItemForm form) {
        User user = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(NotFoundException::new);
        Item item = itemRepository.save(new Item(form, user));
        if (form.getImages() != null) {
            for (MultipartFile image : form.getImages()) {
                try {
                    String fileName = "item_" + System.currentTimeMillis() + StringUtils.cleanPath(image.getOriginalFilename().replaceAll("\\s+", "_"));
                    Storage.Info imageInfo = amazonS3Storage.saveImage(fileName, image, false);
                    ItemImage itemImage = new ItemImage(item, fileName);
                    itemImageRepository.save(itemImage);

                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "image.upload.failed");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "image.upload.failed");
                }
            }
        }
        item.setItemImageList(itemImageRepository.findByItemId(item));
        item.setCategoryId(categoryRepository.findById(form.getCategoryId()));
        System.out.println("downloadUrl---------------------------------------" + downloadUrl);
        return new ItemView(item, downloadUrl);
    }

    @Override
    public List<ItemView> getAll() {
        List<Item> itemList = itemRepository.findByStatusOrderByUpdatedDateDesc(Item.Status.ACTIVE.value);
        return itemList.stream().map(item -> new ItemView(item, downloadUrl)).collect(Collectors.toList());
    }

    @Override
    public List<ItemView> getSellerItems() {
        User currentUser = userRepository.findById(SecurityUtil.getCurrentUserId()).get();
        List<Item> itemList = itemRepository.findAllByStatusAndUserIdOrderByUpdatedDateDesc(Item.Status.ACTIVE.value,currentUser);
        return itemList.stream().map(item -> new ItemView(item, downloadUrl)).collect(Collectors.toList());
    }

    public void downloadImageFile(String fileName, HttpServletResponse response) {
        try {
            logger.info("fileName:----------" + fileName);
            System.out.println("fileName:----------" + fileName);
            response.setHeader("Content-Disposition",
                    "attachment; filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Content-Type",
                    "images/jpeg");
            response.setHeader("X-Download-Options", "noopen");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }

        try {
            InputStream is = amazonS3Storage.get(fileName);
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ItemView updateItem(ItemUpdateForm form) {

        User user = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(NotFoundException::new);
        Item item = itemRepository.findById(form.getId());
        item.update(form, user);
        itemRepository.save(item);
        if (form.getRemoveUrls() != null) {
            for (String url : form.getRemoveUrls()) {
                String imagePath = url.substring(url.lastIndexOf("/media/downloadFile/") + "/media/downloadFile/".length());
                ItemImage itemImage = itemImageRepository.findByImagePath(imagePath);
                if (itemImage != null) {
                    try {
                        amazonS3Storage.delete(itemImage.getImagePath());
                    } catch (IOException ex) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "image.upload.failed");
                    }
                    itemImageRepository.deleteById(itemImage.getId());
                }
            }
        }
        if (form.getImages() != null) {
            for (MultipartFile image : form.getImages()) {
                try {
                    String fileName = "item_" + System.currentTimeMillis() + StringUtils.cleanPath(image.getOriginalFilename().replaceAll("\\s+", "_"));
                    Storage.Info imageInfo = amazonS3Storage.saveImage(fileName, image, false);
                    ItemImage itemImage = new ItemImage(item, fileName);
                    itemImageRepository.save(itemImage);

                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "image.upload.failed");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "image.upload.failed");
                }
            }
        }
        item.setItemImageList(itemImageRepository.findByItemId(item));
        item.setCategoryId(categoryRepository.findById(form.getCategoryId()));
        System.out.println("downloadUrl---------------------------------------" + downloadUrl);
        return new ItemView(item, downloadUrl);
    }

    @Override
    @Transactional
    public ItemView updateItemStatus(StatusUpdateForm form) {
        Item item = itemRepository.findById(form.getId());
        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item.not.found");
        }
        if (!((form.getStatus() == Item.Status.ACTIVE.value) || (form.getStatus() == Item.Status.INACTIVE.value))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "status.not.valid");
        }

        item.setStatus(form.getStatus());
        itemRepository.save(item);
        return new ItemView(item, downloadUrl);
    }

    @Override
    public List<ItemView> getAll(String key, Integer categoryId, Boolean fresh) {

        List<Item> itemList;
        if (StringUtils.isEmpty(key)) {
            key = "";
        }
        itemList = itemRepository.getItemList(Item.Status.ACTIVE.value, key, categoryId, fresh);

        return itemList.stream().map(item -> new ItemView(item, downloadUrl)).collect(Collectors.toList());
    }

}
