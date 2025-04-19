package com.belvinard.gestiondestock.config;

import com.belvinard.gestiondestock.dtos.*;
import com.belvinard.gestiondestock.dtos.CommandeClientDTO;
import com.belvinard.gestiondestock.dtos.LigneCommandeClientDTO;
import com.belvinard.gestiondestock.models.*;
import com.belvinard.gestiondestock.models.CommandeClient;
import com.belvinard.gestiondestock.models.LigneCommandeClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // 📦 CommandeClient → DTO
        TypeMap<CommandeClient, CommandeClientDTO> commandeMap = mapper.createTypeMap(CommandeClient.class,
                CommandeClientDTO.class);
        commandeMap.addMappings(m -> {
            m.map(src -> src.getClient().getId(), CommandeClientDTO::setClientId);
            m.map(src -> src.getEntreprise().getId(), CommandeClientDTO::setEntrepriseId);
            m.map(src -> src.getClient(), CommandeClientDTO::setClientDetails);
            m.map(src -> src.getEntreprise(), CommandeClientDTO::setEntrepriseDetails);
            m.map(src -> src.getLigneCommandeClients(), CommandeClientDTO::setLigneCommandeClients);
        });

        // 🧾 LigneCommandeClient → DTO
        TypeMap<LigneCommandeClient, LigneCommandeClientDTO> ligneMap = mapper.createTypeMap(
                LigneCommandeClient.class, LigneCommandeClientDTO.class);

        ligneMap.addMappings(m -> {
            m.map(src -> src.getArticle(), LigneCommandeClientDTO::setArticleDetails);
            m.map(src -> src.getArticle().getId(), LigneCommandeClientDTO::setArticleId);
            m.map(src -> src.getCommandeClient(), LigneCommandeClientDTO::setCommandeClientDetails);
            m.map(src -> src.getCommandeClient().getId(), LigneCommandeClientDTO::setCommandeClientId);
        });

        // 🎯 Article → DTO
        TypeMap<Article, ArticleDTO> articleMap = mapper.createTypeMap(Article.class, ArticleDTO.class);
        articleMap.addMappings(m -> {
            m.map(src -> src.getCategory().getId(), ArticleDTO::setCategoryId);
            m.map(src -> src.getCategory(), ArticleDTO::setCategoryDetails);
        });


        // Fournisseur → DTO
        TypeMap<Fournisseur, FournisseurDTO> fournisseurToDto = mapper.createTypeMap(Fournisseur.class, FournisseurDTO.class);
        fournisseurToDto.addMappings(m -> {
            m.map(Fournisseur::getAdresse, FournisseurDTO::setAdresse);
            m.map(f -> f.getEntreprise().getId(), FournisseurDTO::setEntrepriseId);
            m.map(Fournisseur::getEntreprise, FournisseurDTO::setEntrepriseDetails);
        });


        // FournisseurDTO → Entité
        TypeMap<FournisseurDTO, Fournisseur> dtoToFournisseur = mapper.createTypeMap(FournisseurDTO.class,
                Fournisseur.class);
        dtoToFournisseur.addMappings(m -> {
            m.map(FournisseurDTO::getAdresse, Fournisseur::setAdresse);
        });

        // ✅ CommandeFournisseur → DTO
        TypeMap<CommandeFournisseur, CommandeFournisseurDTO> commandeFournisseurMap = mapper.createTypeMap(CommandeFournisseur.class,
                CommandeFournisseurDTO.class);
        commandeFournisseurMap.addMappings(m -> {
            m.map(src -> src.getFournisseur().getId(), CommandeFournisseurDTO::setFournisseurId);
            m.map(CommandeFournisseur::getFournisseur, CommandeFournisseurDTO::setFournisseurDetails);
        });

        // 📦 LigneCommandeFournisseur → DTO
        TypeMap<LigneCommandeFournisseur, LigneCommandeFournisseurDTO> ligneCmdFournisseurMap =
                mapper.createTypeMap(LigneCommandeFournisseur.class, LigneCommandeFournisseurDTO.class);

        ligneCmdFournisseurMap.addMappings(m -> {
            m.map(src -> src.getCommandeFournisseur().getId(), LigneCommandeFournisseurDTO::setCommandeFournisseurId);
            m.map(LigneCommandeFournisseur::getCommandeFournisseur, LigneCommandeFournisseurDTO::setCommandeFournisseurDetails);
            m.map(src -> src.getArticle().getId(), LigneCommandeFournisseurDTO::setArticleId);
            m.map(LigneCommandeFournisseur::getArticle, LigneCommandeFournisseurDTO::setArticleDetails);
        });

//        TypeMap<Ventes, VentesDTO> ventesMap = mapper.createTypeMap(Ventes.class, VentesDTO.class);
//        ventesMap.addMappings(m -> {
//            m.map(Ventes::getIdEntreprise, VentesDTO::setEntrepriseId);
//            m.map(Ventes::getLigneVentes, VentesDTO::setLigneVentes);
//            m.map(Ventes::getDateVente, VentesDTO::setDateVente); // 👈 à ajouter dans le DTO
//        });
//
//        TypeMap<VentesDTO, Ventes> ventesDtoToEntity = mapper.createTypeMap(VentesDTO.class, Ventes.class);
//        ventesDtoToEntity.addMappings(m -> {
//            m.map(VentesDTO::getEntrepriseId, Ventes::setIdEntreprise);
//            m.map(VentesDTO::getDateVente, Ventes::setDateVente); // 👈 aussi ici
//        });

//        TypeMap<LigneVente, LigneVenteDTO> ligneVenteMap = mapper.createTypeMap(LigneVente.class, LigneVenteDTO.class);
//        ligneVenteMap.addMappings(m -> {
//            m.map(src -> src.getArticle(), LigneVenteDTO::setArticle);
//            m.map(src -> src.getEntreprise().getId(), LigneVenteDTO::setEntrepriseId);
//            m.map(src -> src.getVente().getId(), LigneVenteDTO::setVenteId); // à ajouter dans DTO si nécessaire
//
//        });

        TypeMap<LigneVente, LigneVenteDTO> ligneVenteMap = mapper.createTypeMap(LigneVente.class, LigneVenteDTO.class);
        ligneVenteMap.addMappings(m -> {
            m.map(src -> src.getArticle().getId(), LigneVenteDTO::setArticleId);
            m.map(src -> src.getArticle(), LigneVenteDTO::setArticleDetails);
            m.map(src -> src.getVente().getId(), LigneVenteDTO::setVenteId);
            m.map(src -> src.getVente(), LigneVenteDTO::setVenteDetails);
            m.map(src -> src.getEntreprise().getId(), LigneVenteDTO::setEntrepriseId);
            m.map(src -> src.getEntreprise(), LigneVenteDTO::setEntrepriseDetails); // 👈 Ici
        });







        return mapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Pour les dates type Instant, LocalDateTime
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}

