package com.belvinard.gestiondestock.services.impl;

import com.belvinard.gestiondestock.dtos.LigneCommandeFournisseurDTO;
import com.belvinard.gestiondestock.exceptions.BusinessRuleException;
import com.belvinard.gestiondestock.exceptions.ResourceNotFoundException;
import com.belvinard.gestiondestock.models.Article;
import com.belvinard.gestiondestock.models.CommandeFournisseur;
import com.belvinard.gestiondestock.models.EtatCommande;
import com.belvinard.gestiondestock.models.LigneCommandeFournisseur;
import com.belvinard.gestiondestock.repositories.ArticleRepository;
import com.belvinard.gestiondestock.repositories.CommandeFournisseurRepository;
import com.belvinard.gestiondestock.repositories.LigneCommandeFournisseurRepository;
import com.belvinard.gestiondestock.services.LigneCommandeFournisseurService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LigneCommandeFournisseurServiceImpl implements LigneCommandeFournisseurService {

    private final LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private final CommandeFournisseurRepository commandeFournisseurRepository;
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    @Override
    public LigneCommandeFournisseurDTO save(LigneCommandeFournisseurDTO dto, Long commandeFournisseurId, Long articleId) {
        // Vérifier l'existence de la commande fournisseur
        CommandeFournisseur commande = commandeFournisseurRepository.findById(commandeFournisseurId)
                .orElseThrow(() -> new ResourceNotFoundException("CommandeFournisseur", "ID", commandeFournisseurId));

        // Vérifier l'existence de l'article
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "ID", articleId));

        // Empêcher les ajouts si la commande est déjà livrée
        if (commande.getEtatCommande() == EtatCommande.LIVREE) {
            throw new BusinessRuleException("Impossible d'ajouter une ligne : la commande est déjà livrée.");
        }

        // Mapper le DTO vers l'entité
        LigneCommandeFournisseur ligne = modelMapper.map(dto, LigneCommandeFournisseur.class);
        ligne.setCommandeFournisseur(commande);
        ligne.setArticle(article);

        // Calcul automatique du TTC si le HT + TVA est présent
        if (ligne.getPrixUnitaireHt() != null && ligne.getTauxTva() != null) {
            BigDecimal tva = ligne.getPrixUnitaireHt().multiply(ligne.getTauxTva()).divide(BigDecimal.valueOf(100));
            ligne.setPrixUnitaireTtc(ligne.getPrixUnitaireHt().add(tva));
        }

        LigneCommandeFournisseur saved = ligneCommandeFournisseurRepository.save(ligne);
        return modelMapper.map(saved, LigneCommandeFournisseurDTO.class);
    }

    @Override
    public List<LigneCommandeFournisseurDTO> findAllByCommandeId(Long idCommande) {
        return ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande).stream()
                .map(ligne -> modelMapper.map(ligne, LigneCommandeFournisseurDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public LigneCommandeFournisseurDTO deleteById(Long idLigneCommande) {
        LigneCommandeFournisseur ligne = ligneCommandeFournisseurRepository.findById(idLigneCommande)
                .orElseThrow(() -> new ResourceNotFoundException("LigneCommandeFournisseur", "ID", idLigneCommande));

        // Vérifier si la commande est livrée avant de supprimer
        if (ligne.getCommandeFournisseur().getEtatCommande() == EtatCommande.LIVREE) {
            throw new BusinessRuleException("Suppression impossible : la commande fournisseur est déjà livrée.");
        }

        ligneCommandeFournisseurRepository.delete(ligne);
        return modelMapper.map(ligne, LigneCommandeFournisseurDTO.class);
    }

    @Override
    public List<LigneCommandeFournisseurDTO> findHistoriqueByArticleId(Long idArticle) {
        return ligneCommandeFournisseurRepository.findAllByArticleId(idArticle).stream()
                .map(ligne -> modelMapper.map(ligne, LigneCommandeFournisseurDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LigneCommandeFournisseurDTO> findById(Long idLigneCommande) {
        return ligneCommandeFournisseurRepository.findById(idLigneCommande)
                .map(ligne -> modelMapper.map(ligne, LigneCommandeFournisseurDTO.class));
    }
}
