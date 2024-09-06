package com.globallogic.jmpaniego.msusers.model;

import com.globallogic.jmpaniego.msusers.model.dto.PhoneDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long number;
    private Integer cityCode;
    private String countryCode;

    public Phone(PhoneDTO phoneDTO) {
        this.number = phoneDTO.getNumber();
        this.cityCode = phoneDTO.getCityCode();
        this.countryCode = phoneDTO.getCountryCode();
    }
}
