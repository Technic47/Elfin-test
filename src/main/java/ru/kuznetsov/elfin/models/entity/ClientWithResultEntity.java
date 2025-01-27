package ru.kuznetsov.elfin.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import ru.kuznetsov.elfin.models.dto.ClientDto;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "client-with-result")
public class ClientWithResultEntity {

    @Id
    @Field(type = FieldType.Text, name = "inn")
    private String inn;

    @Field(type = FieldType.Integer, name = "region")
    private Integer region;

    @Field(type = FieldType.Text, name = "capital")
    private String capital;

    @Field(type = FieldType.Boolean, name = "result")
    private Boolean result;

    public ClientWithResultEntity(ClientDto clientInfo, boolean result) {
        this.inn = clientInfo.getInn();
        this.region = clientInfo.getRegion();
        this.capital = clientInfo.getCapital().toString();
        this.result = result;
    }
}
