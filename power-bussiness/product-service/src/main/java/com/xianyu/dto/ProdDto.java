package com.xianyu.dto;

import com.xianyu.domain.Prod;
import com.xianyu.domain.Sku;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.dto
 * @Author: xianyu
 * @CreateTime: 2023-06-26  10:26
 * @Description:
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商品Dto类")
public class ProdDto extends Prod {
    @ApiModelProperty("商品标签集合")
    private List<Long> tagList;
    @ApiModelProperty("sku集合")
    private List<Sku> skuList;
    @ApiModelProperty("配送方式")
    private DeliveryMode deliveryModeVo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("配送方式")
    // 【内部类DeliveryMode】
    public static class DeliveryMode {
        @ApiModelProperty("商家配送")
        private Boolean hasShopDelivery;
        @ApiModelProperty("用户自提")
        private Boolean hasUserPickUp;
    }
}
