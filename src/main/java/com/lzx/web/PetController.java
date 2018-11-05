package com.lzx.web;

import com.lzx.dao.PetMapper;
import com.lzx.entity.Pet;
import com.lzx.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetMapper petMapper;

    @GetMapping
    public String getIndex(Model model) {
        return "petIndex";
    }

    private Pet p = null;

    @PostMapping("/all")
    @ResponseBody
    public List<Pet> getAll(Model model) {
        List<Pet> list = petMapper.selectAll();
        return list;
    }


    @PostMapping
    @ResponseBody
    public ResponseEntity addPet(Pet pet) {
        int num = petMapper.insert(pet);
        if (num != 0) {
            return ResponseEntity.status(200).body(pet);
        } else {
            return ResponseEntity.status(405).body(new ApiResponse(1, "error", "Invalid input"));
        }
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity updatePet(Pet pet) {
        if (pet.getId() != null) {
            if (petMapper.selectByPrimaryKey(pet.getId()) != null) {
                int num = petMapper.updateByPrimaryKey(pet);
                if (num != 0) {
                    return ResponseEntity.status(200).body(pet);
                } else {
                    return ResponseEntity.status(405).body(new ApiResponse(3, "error", "Validation exception"));
                }
            } else {
                return ResponseEntity.status(404).body(new ApiResponse(1, "error", "Pet not found"));
            }
        } else {
            return ResponseEntity.status(400).body(new ApiResponse(2, "error", "Invalid ID supplied"));
        }
    }

    @GetMapping("/findByStatus")
    @ResponseBody
    public ResponseEntity findByStatus(String status) {
        List<Pet> list = null;
        if (status == null || status.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse(2, "error", "Invalid Status supplied"));
        } else {
            list = petMapper.selectByStatus(status);
            if (list != null) {
                return ResponseEntity.status(200).body(list);
            } else {
                return ResponseEntity.status(404).body(new ApiResponse(1, "error", "Pet not found"));
            }
        }
    }

    @GetMapping("/{petId}")
    @ResponseBody
    public ResponseEntity findById(@PathVariable int petId) {
        if (petId == 0) {
            return ResponseEntity.status(400).body(new ApiResponse(2, "error", "Invalid ID Value"));
        } else {
            Pet pet = petMapper.selectByPrimaryKey(petId);
            if (pet != null) {
                return ResponseEntity.status(200).body(pet);
            } else {
                return ResponseEntity.status(405).body(new ApiResponse(1, "error", "Invalid input"));
            }
        }
    }

    @PostMapping("/{petId}")
    @ResponseBody
    public ResponseEntity updateById(@PathVariable int petId, Pet pet) {
        if (petId == 0) {
            return ResponseEntity.status(405).body(new ApiResponse(1, "error", "Invalid input"));
        } else {
            int num = petMapper.updateByPrimaryKey(pet);
            return ResponseEntity.status(200).body(pet);
        }
    }

    @DeleteMapping("/{petId}")
    @ResponseBody
    public ResponseEntity delById(@PathVariable int petId) {
        if (petMapper.selectByPrimaryKey(petId) == null) {
            return ResponseEntity.status(405).body(new ApiResponse(1, "error", "Pet not found"));
        } else {
            if (petMapper.deleteByPrimaryKey(petId) == 0) {
                return ResponseEntity.status(400).body(new ApiResponse(2, "error", "Invalid ID supplied"));
            } else {
                return ResponseEntity.status(200).body(new ApiResponse(200, "", "successful operation"));
            }
        }
    }

    @PostMapping("/{petId}/uploadImage")
    @ResponseBody
    public ResponseEntity uploadImage(@PathVariable int petId, @RequestPart("file") MultipartFile file, HttpServletRequest re, Model model) {
        String contextType = file.getContentType();
        if (!contextType.contains("image/")) {
            return ResponseEntity.status(400).body(new ApiResponse(1, "", "只能上传图片"));
        }
        if (file.getSize() > 1024 * 1024 * 5) {
            return ResponseEntity.status(400).body(new ApiResponse(1, "", "文件过大"));
        }
        try {
            String path = re.getServletContext().getRealPath(File.separator + "img");
            File img = new File(path);
            if (!img.exists()) {
                img.mkdir();
            }
            String newName = path + File.separator+getNewName(file.getOriginalFilename());
            File fileName = new File(newName);
            file.transferTo(fileName);

            if (petMapper.upLoadImg(new Pet(petId, newName)) != 0) {
                return ResponseEntity.status(200).body(new ApiResponse(200, "unknown", "additionalMetadata: " + new Date() + "\\nFile uploaded to " + newName));
            }

        } catch (IOException e) {
            return ResponseEntity.status(400).body(new ApiResponse(1, "", "上传失败"));
            //e.printStackTrace();
        }
        return ResponseEntity.status(400).body(new ApiResponse(1, "", "上传失败"));
    }

    public String getNewName(String name) {
        int index = name.lastIndexOf(".");
        String firstName = name.substring(0, index);
        String lastName = name.substring(index);
        String date = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
        return  firstName + "_" + date + lastName;
    }
}
