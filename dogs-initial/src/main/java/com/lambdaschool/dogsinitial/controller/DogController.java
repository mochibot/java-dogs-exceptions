package com.lambdaschool.dogsinitial.controller;

import com.lambdaschool.dogsinitial.DogsinitialApplication;
import com.lambdaschool.dogsinitial.exception.ResourceNotFoundException;
import com.lambdaschool.dogsinitial.model.Dog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@RestController
@RequestMapping("/dogs")
public class DogController
{
    // localhost:8080/dogs/dogs
    @GetMapping(value = "/dogs", produces={"application/json"})
    public ResponseEntity<?> getAllDogs()
    {
        return new ResponseEntity<>(DogsinitialApplication.ourDogList.dogList, HttpStatus.OK);
    }

    // localhost:8080/dogs/{id}
    @GetMapping(value = "/{id}", produces={"application/json"})
    public ResponseEntity<?> getDogDetail(@PathVariable long id)
    {
        Dog rtnDog;
        if (DogsinitialApplication.ourDogList.findDog(d -> (d.getId() == id)) == null)
        {
            throw new ResourceNotFoundException("Dog with id " + id + " not found");
        }
        else
        {
            rtnDog = DogsinitialApplication.ourDogList.findDog(d -> (d.getId() == id));
        }
        return new ResponseEntity<>(rtnDog, HttpStatus.OK);
    }

    // localhost:8080/dogs/breeds/{breed}
    @GetMapping(value = "/breeds/{breed}", produces={"application/json"})
    public ResponseEntity<?> getDogBreeds (@PathVariable String breed)
    {
        ArrayList<Dog> rtnDogs = DogsinitialApplication.ourDogList.
                findDogs(d -> d.getBreed().toUpperCase().equals(breed.toUpperCase()));

        if (rtnDogs.size() == 0)
        {
            throw new ResourceNotFoundException("No dog of breed " + breed);
        }
        return new ResponseEntity<>(rtnDogs, HttpStatus.OK);
    }

    //localhost:8080/dogs/dogstablebybreed
    @GetMapping(value="/dogstablebybreed")
    public ModelAndView displayDogTableByBreed()
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dogs");    //Thymeleaf looks for file with name dogs under resources > templates
        DogsinitialApplication.ourDogList.dogList.sort((d1, d2) -> d1.getBreed().compareToIgnoreCase(d2.getBreed()));
        mav.addObject("dogsList", DogsinitialApplication.ourDogList.dogList);   //match object name in html file
        return mav;
    }

    //localhost:8080/dogs/dogstablebyaptsuitable
    @GetMapping(value="/dogstablebyaptsuitable")
    public ModelAndView displayDogTableByAptSuitable()
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dogs");    //Thymeleaf looks for file with name dogs under resources > templates
        DogsinitialApplication.ourDogList.dogList.sort((d1, d2) -> Boolean.compare(d1.getApartmentSuitable(), d2.getApartmentSuitable()));
        mav.addObject("dogsList", DogsinitialApplication.ourDogList.dogList);   //match object name in html file
        return mav;
    }
}
