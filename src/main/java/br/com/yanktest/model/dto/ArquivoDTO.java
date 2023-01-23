package br.com.yanktest.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ArquivoDTO {

    @SerializedName(value = "row")
    private int row;

    @SerializedName("qtd_nicks")
    private String nicks;

    @SerializedName("qtd_letras")
    private String letras;

}
