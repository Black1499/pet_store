package com.lzx.web;

import com.lzx.dao.PetMapper;
import com.lzx.entity.Pet;
import com.lzx.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetMapper petMapper;

    @GetMapping
    public String getIndex(Model model) {
        return "petIndex";
    }

    private Pet pet = null;

    @PostMapping("/all")
    @ResponseBody
    public List<Pet> getAll(Model model) {
        List<Pet> list = petMapper.selectAll();
        return list;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity addPet(Pet pet) {
        pet = petMapper.insert(pet);
        if (pet != null) {
            return ResponseEntity.status(200).body(pet);
        } else {
            return ResponseEntity.status(405).body(new ApiResponse(1, "error", "Invalid input"));
        }
    }


    @PutMapping
    @ResponseBody
    public ResponseEntity updatePet(Pet pet) {
        if (pet.getId() == null) {
            if (petMapper.selectByPrimaryKey(pet.getId()) != null) {
                pet = petMapper.updateByPrimaryKey(pet);
                if ( pet!=null) {
                    return ResponseEntity.status(200).body(pet);
                } else {
                    return ResponseEntity.status(405).body(new ApiResponse(3, "error", "Validation exception");
                }
            } else {
                return  ResponseEntity.status(404).body(new ApiResponse(1, "error", "Pet not found"));
            }
        } else {
            return ResponseEntity.status(400).body(new ApiResponse(2, "error", "Invalid ID supplied"));
        }
    }

    @GetMapping("/findByStatus")
    @ResponseBody
    public ResponseEntity findByStatus(Pet pet) {
        List<Pet> list = null;
        if (pet.getStatus() == null || pet.getStatus().isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse(2, "error", "Invalid Status supplied"));
        } else {
            list = petMapper.selectByStatus(pet.getStatus());
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
            return ResponseEntity.status(400).body(new ApiResponse(2,"error","Invalid ID Value"));
        } else {
            Pet pet = petMapper.selectByPrimaryKey(petId);
            if (pet != null) {
                return ResponseEntity.status(200).body(pet);
            } else {
                return ResponseEntity.status(405).body(new ApiResponse(1,"error","Invalid input"));
            }
        }
    }

    @PostMapping("/{petId}")
    @ResponseBody
    public ResponseEntity updateById(@PathVariable int petId, Pet pet) {
        if (petId == 0) {
            return ResponseEntity.status(405).body(new ApiResponse(1,"error","Invalid input"));
        } else {
            pet = petMapper.updateByPrimaryKey(pet);
            return ResponseEntity.status(200).body(pet);
        }
    }

    @DeleteMapping("/{petId}")
    @ResponseBody
    public ResponseEntity delById(@PathVariable int petId) {
        pet.setId(petId);
        if (petMapper.selectByPrimaryKey(pet.getId()) == null) {
            return ResponseEntity.status(405).body(new ApiResponse(1,"error","Pet not found"));
        } else {
            if (petMapper.deleteByPrimaryKey(petId) == 0) {
                return ResponseEntity.status(400).body(new ApiResponse(2,"error", "Invalid ID supplied"));
            } else {
                return ResponseEntity.status(200).body(new ApiResponse(200, "", "successful operation"));
            }
        }
    }

    @PostMapping("/{petId}/uploadImage")
    @ResponseBody
    public ResponseEntity uploadImage(@PathVariable int petId, Pet pet) {
        if (petMapper.upLoadImg(pet) != 0) {
            return  ResponseEntity.status(200).body(new ApiResponse(200,"unknown","additionalMetadata: "+new Date()+"\\nFile uploaded to "+pet.getPhoto_urls()));
        }
        return ResponseEntity.status(404).body(null);
    }
}
