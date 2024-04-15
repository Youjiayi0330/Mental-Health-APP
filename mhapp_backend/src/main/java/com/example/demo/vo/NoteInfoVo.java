package com.example.demo.vo;

import com.example.demo.entity.NoteInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteInfoVo extends NoteInfo {
    String userName;
    Integer identity;
}
