//package com.belvinard.gestiondestock.config;
//
//import com.belvinard.gestiondestock.dtos.*;
//import com.belvinard.gestiondestock.dtos.CommandeClientDTO;
//import com.belvinard.gestiondestock.dtos.LigneCommandeClientDTO;
//import com.belvinard.gestiondestock.models.*;
//import com.belvinard.gestiondestock.models.CommandeClient;
//import com.belvinard.gestiondestock.models.LigneCommandeClient;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.TypeMap;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AppConfig {
//
//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper mapper = new ModelMapper();
//
//        // 📦 CommandeClient → DTO
//        TypeMap<CommandeClient, CommandeClientDTO> commandeMap = mapper.createTypeMap(CommandeClient.class, CommandeClientDTO.class);
//        commandeMap.addMappings(m -> {
//            m.map(src -> src.getClient().getId(), CommandeClientDTO::setClientId);
//            m.map(src -> src.getEntreprise().getId(), CommandeClientDTO::setEntrepriseId);
//            m.map(src -> src.getClient(), CommandeClientDTO::setClientDetails);
//            m.map(src -> src.getEntreprise(), CommandeClientDTO::setEntrepriseDetails);
//            m.map(src -> src.getLigneCommandeClients(), CommandeClientDTO::setLigneCommandeClients);
//        });
//
//        // 🧾 LigneCommandeClient → DTO
//        TypeMap<LigneCommandeClient, LigneCommandeClientDTO> ligneMap = mapper.createTypeMap(LigneCommandeClient.class, LigneCommandeClientDTO.class);
//        ligneMap.addMappings(m -> {
//            m.map(src -> src.getArticle(), LigneCommandeClientDTO::setArticleDetails);
//            m.map(src -> src.getCommandeClient().getId(), LigneCommandeClientDTO::setCommandeClientId);
//            m.map(src -> src.getCommandeClient(), LigneCommandeClientDTO::setCommandeClientDetails);
//            m.map(src -> src.getEntreprise().getId(), LigneCommandeClientDTO::setEntrepriseId);
//            m.map(src -> src.getEntreprise(), LigneCommandeClientDTO::setEntrepriseDetails);
//        });
//
//        // 🎯 Article → DTO
//        TypeMap<Article, ArticleDTO> articleMap = mapper.createTypeMap(Article.class, ArticleDTO.class);
//        articleMap.addMappings(m -> {
//            m.map(src -> src.getCategory().getId(), ArticleDTO::setCategoryId);
//            m.map(src -> src.getCategory(), ArticleDTO::setCategoryDetails);
//        });
//
//
//        // Fournisseur → DTO
//        TypeMap<Fournisseur, FournisseurDTO> fournisseurToDto = mapper.createTypeMap(Fournisseur.class, FournisseurDTO.class);
//        fournisseurToDto.addMappings(m -> {
//            m.map(Fournisseur::getAdresse, FournisseurDTO::setAdresse);
//            m.map(f -> f.getEntreprise().getId(), FournisseurDTO::setEntrepriseId);
//            m.map(Fournisseur::getEntreprise, FournisseurDTO::setEntrepriseDetails);
//        });
//
//
//        // FournisseurDTO → Entité
//        TypeMap<FournisseurDTO, Fournisseur> dtoToFournisseur = mapper.createTypeMap(FournisseurDTO.class, Fournisseur.class);
//        dtoToFournisseur.addMappings(m -> {
//            m.map(FournisseurDTO::getAdresse, Fournisseur::setAdresse);
//        });
//
//        // Mapping enrichi de LigneCommandeFournisseur vers DTO
//        TypeMap<LigneCommandeFournisseur, LigneCommandeFournisseurDTO> typeMap = mapper.createTypeMap(
//                LigneCommandeFournisseur.class, LigneCommandeFournisseurDTO.class);
//
//        typeMap.addMappings(m -> {
//            m.map(src -> src.getArticle().getId(), LigneCommandeFournisseurDTO::setArticleId);
//            m.map(LigneCommandeFournisseur::getArticle, LigneCommandeFournisseurDTO::setArticleDetails);
//            m.map(src -> src.getCommandeFournisseur().getId(), LigneCommandeFournisseurDTO::setCommandeFournisseurId);
//            m.map(src -> src.getCommandeFournisseur().getEntreprise().getId(), LigneCommandeFournisseurDTO::setEntrepriseId);
//        });
//
//
//        return mapper;
//    }
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule()); // Pour les dates type Instant, LocalDateTime
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        return mapper;
//    }
//}
