package com.belvinard.gestiondestock.services;

import com.belvinard.gestiondestock.dtos.MvtStkDTO;

import java.math.BigDecimal;
import java.util.List;

public interface MvtStkService {

  BigDecimal stockReelArticle(Long idArticle);

//  BigDecimal stockReelArticle(Long idArticle);
//
//  List<MvtStkDTO> mvtStkArticle(Long idArticle);
//
//  MvtStkDTO entreeStock(MvtStkDTO mvtStkDTO);
//
//  MvtStkDTO sortieStock(MvtStkDTO mvtStkDTO);
//
//  MvtStkDTO correctionStockPos(MvtStkDTO mvtStkDTO);
//
//  MvtStkDTO correctionStockNeg(MvtStkDTO mvtStkDTO);


}
