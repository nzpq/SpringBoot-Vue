package com.nzpq.controller;

import com.nzpq.entity.Emp;
import com.nzpq.service.EmpService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@CrossOrigin
@ResponseBody
public class EmpController {

    @Autowired
    private EmpService empService;

    @Value("${photo.dir}")//注入图片存储路径
    private String realPath;

    //日志
    Logger logger = LoggerFactory.getLogger(EmpController.class);

    /**
     * 更新
     * @param emp
     * @param photo
     * @return
     * @throws IOException
     */
    @PostMapping("/update")
    public Map<String,Object> update( Emp emp, MultipartFile photo) throws IOException {

//        logger.info("修改员工信息 --> "+emp.toString());
//        logger.info("照片信息 --> "+photo.getOriginalFilename());

        Map<String,Object> map = new HashMap<>();

        try {
            if(photo != null && photo.getSize() != 0){
                //删除员工照片
                Emp  e = empService.findOne(Integer.valueOf(emp.getId()));
                File file = new File(realPath,e.getPhotoPath());
                if(file.exists()){
                    //如果图片存在，则删除
                    file.delete();
                }
                //保存图片
                //FilenameUtils.getExtension( ) 获取文件扩展名
                String newFileName = UUID.randomUUID().toString()+"."+ FilenameUtils.getExtension(photo.getOriginalFilename());
                photo.transferTo(new File(realPath,newFileName));

                //设置该用户照片路径
                emp.setPhotoPath(newFileName);
            }

            //保存
            empService.update(emp);
            map.put("state",true);
            map.put("message","修改成功");
        } catch (Exception e) {
//            e.printStackTrace();
            map.put("state",false);
            map.put("message","修改失败");
            logger.error(e.toString());
        }


        return map;
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Map<String,Object> delete( String id){

        Map<String,Object> map = new HashMap<>();

        try {
            //删除员工照片
            Emp emp = empService.findOne(Integer.valueOf(id));
            File file = new File(realPath,emp.getPhotoPath());
            if(file.exists()){
                //如果图片存在，则删除
                file.delete();
            }
            //删除员工信息
            empService.delete(Integer.valueOf(id));
            logger.error("file --> "+file);

            map.put("state",true);
            map.put("message","删除成功");
        } catch (Exception e) {
            map.put("state",true);
            map.put("message","删除失败");
            logger.error(e.toString());
        }
        return map;
    }

    /**
     * 添加员工
     * @param emp
     * @param photo
     * @return
     * @throws IOException
     */
    @PostMapping("/emp")
    public Map<String,Object> save( Emp emp, MultipartFile photo) throws IOException {

//        logger.info("添加员工信息 --> "+emp.toString());
//        logger.info("照片信息 --> "+photo.getOriginalFilename());

        Map<String,Object> map = new HashMap<>();

        try {
            //保存图片
            //FilenameUtils.getExtension( ) 获取文件扩展名
            String newFileName = UUID.randomUUID().toString()+"."+ FilenameUtils.getExtension(photo.getOriginalFilename());
            photo.transferTo(new File(realPath,newFileName));

            //设置该用户照片路径
            emp.setPhotoPath(newFileName);

            //保存
            empService.save(emp);
            map.put("state",true);
            map.put("message","添加成功");
        } catch (Exception e) {
//            e.printStackTrace();
            map.put("state",false);
            map.put("message","添加失败");
            logger.error(e.toString());
        }


        return map;
    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/emps")
    public List<Emp> findAll(){
        List<Emp> emps = null;
        try {
            emps = empService.findAll();
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return emps;
    }

    @GetMapping("/findOne")
    public Emp findOne(String id){
        return empService.findOne(Integer.valueOf(id));
    }
}
