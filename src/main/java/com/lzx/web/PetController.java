package com.lzx.web;

import com.lzx.dao.PetMapper;
import com.lzx.entity.Pet;
import com.lzx.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetMapper petMapper;

    @PostMapping
    @ResponseBody
    public ApiResponse addPet(Pet pet) {
        if (petMapper.insert(pet) != 0) {
            return new ApiResponse();
        } else {
            return new ApiResponse(405, "error", "Invalid input");
        }
    }


    @PutMapping
    @ResponseBody
    public ApiResponse updatePet(Pet pet) {
        if (pet.getId() == null) {
            if (petMapper.selectByPrimaryKey(pet.getId()) != null) {
                if (petMapper.updateByPrimaryKey(pet) != 0) {
                    return new ApiResponse();
                } else {
                    return new ApiResponse(405, "error", "Validation exception");
                }
            } else {
                return new ApiResponse(404, "error", "Pet not found");
            }
        } else {
            return new ApiResponse(400, "error", "Invalid ID supplied");
        }
    }

    @GetMapping("/findByStatus")
    @ResponseBody
    public ApiResponse findByStatus(Pet pet) {
        List<Pet> list = null;
        if (pet.getStatus() == null || pet.getStatus().isEmpty()) {
            return new ApiResponse(400, "error", "Invalid status value");
        } else {
            list = petMapper.selectByStatus(pet.getStatus());
            if (list == null) {
                return new ApiResponse(200, "success", "successful operation");
            } else {
                return new ApiResponse();
            }
        }
    }

    @GetMapping("/{petId}")
    @ResponseBody
    public ApiResponse findById(@PathVariable int petId) {
        if (petId == 0) {
            return new ApiResponse(400, "error", "Invalid ID value");
        } else {
            if (petMapper.selectByPrimaryKey(petId) != null) {
                return new ApiResponse(200, "error", "successful operation");
            } else {
                return new ApiResponse(404, "error", "Pet not found");
            }
        }
    }

    @PostMapping("/{petId}")
    @ResponseBody
    public ApiResponse updateById(@PathVariable int petId, Pet pet) {
        if (petId == 0) {
            return new ApiResponse(405, "error", "Invalid input");
        } else {
            petMapper.updateByPrimaryKey(pet);
            return new ApiResponse();
        }
    }

    @DeleteMapping("/{petId}")
    @ResponseBody
    public ApiResponse delById(@PathVariable int petId) {
        Pet pet = new Pet();
        pet.setId(petId);
        if (petMapper.selectByPrimaryKey(pet.getId()) == null) {
            return new ApiResponse(405, "error", "Pet not found");
        } else {
            if (petMapper.deleteByPrimaryKey(petId) == 0) {
                return new ApiResponse(400, "error", "Invalid ID supplied");
            } else {
                return new ApiResponse();
            }
        }
    }

    @PostMapping("/{petId}/uploadImage")
    @ResponseBody
    public ApiResponse uploadImage(@PathVariable int petId, Pet pet) {
        if (petMapper.updateByPrimaryKey(pet) != 0) {
            return new ApiResponse(200, "error", "successful operation");
        }
        return new ApiResponse();
    }


}
