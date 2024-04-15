package com.example.demo.vo;

import com.example.demo.entity.Passage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassageVo extends Passage {
    private Integer collect_count;
}
