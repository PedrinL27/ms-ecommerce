package com.pedrin.pedidos.controller.mappers;

import com.pedrin.pedidos.controller.dto.ItemPedidoDTO;
import com.pedrin.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedido toEntity(ItemPedidoDTO dto);
}
