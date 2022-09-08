package edu.xsyu.onlinesubmit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDto {
    public String name;
    public String category;
    public String language;
    public String info;
    public String organizer;
    public String issn;
    public String publicationFrequency;
}
