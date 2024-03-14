package ProjetDorsal.projetDorsal.Utilitaire;

import ProjetDorsal.projetDorsal.Dto.CorrectionDto;
import ProjetDorsal.projetDorsal.Entity.CorrectionEntity;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class CorrectionMappage {


    private UserMappage userMappage;


    public CorrectionDto entityToDto(@NotNull CorrectionEntity correctionEntity)
    {
         CorrectionDto correctionDto = new CorrectionDto();

         correctionDto.setId(correctionEntity.getId());
         correctionDto.setSaisiday(correctionEntity.getSaisiday());
         correctionDto.setSaisinext(correctionEntity.getSaisinext());
         correctionDto.setCorrectionvalue(correctionEntity.getCorrectionvalue());
         correctionDto.setUserDto(userMappage.entityToDto(correctionEntity.getUserentity()));

         return correctionDto;
    }


    public CorrectionEntity dtoToEntity(@NotNull CorrectionDto correctionDto)
    {
        CorrectionEntity correctionEntity = new CorrectionEntity();


        correctionEntity.setId(correctionDto.getId());
        correctionEntity.setSaisiday(correctionDto.getSaisiday());
        correctionEntity.setSaisinext(correctionDto.getSaisinext());
        correctionEntity.setCorrectionvalue(correctionDto.getCorrectionvalue());
        correctionEntity.setUserentity(userMappage.dtoToEntityforCorrection(correctionDto.getUserDto()));

        return correctionEntity;
    }


    public List<CorrectionDto> listeentityToDto(@NotNull List<CorrectionEntity> correctionEntities)
    {
        List<CorrectionDto> correctionDtos = new ArrayList<>();

        CorrectionDto correctionDto;
        for (CorrectionEntity correctionEntity : correctionEntities)
        {
            correctionDto = entityToDto(correctionEntity);
            correctionDtos.add(correctionDto);
        }
        return correctionDtos;
    }

}
