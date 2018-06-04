package com.ryz.photoalbum.repository;

import com.ryz.photoalbum.pojo.PImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name="p_image")
public interface ImageRepository extends CrudRepository<PImage,String> {

    List<PImage> findPImagesByUserId(String userId);

    @Query("select image.id from PImage image where image.userId like ?1")
    List<String> getALLId(String userId);

    @Query("select image from PImage image where image.id in ?1")
    List<PImage> getRandomImages(List<String> list);
}
