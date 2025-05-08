package com.bridgelab.hiringapp.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class DocumentDto {

    private String documentType; // resume , letter
    private String fileUrl; // url path of file location
    private boolean verified ; // default to false

}
