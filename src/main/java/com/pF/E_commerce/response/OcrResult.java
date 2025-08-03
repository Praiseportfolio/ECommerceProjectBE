package com.pF.E_commerce.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OcrResult {
    private List<String> lines;

    public OcrResult(List<String> lines) {
        this.lines = lines;
    }

}
