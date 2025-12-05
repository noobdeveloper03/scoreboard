package com.beyblade.scoreboard.service.impl;

import com.beyblade.scoreboard.constant.CellCodes;
import com.beyblade.scoreboard.dto.Player;
import com.beyblade.scoreboard.exception.BeyBladeNotFoundException;
import com.beyblade.scoreboard.service.ScoreService;
import com.beyblade.scoreboard.middleware.GoogleSheetApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements ScoreService {

    private String SHEET_NAME = "Sheet1";

    @Autowired
    private GoogleSheetApiService sheetApi;

    @Override
    public void recordScore(Player playerA, Player playerB) {
        try {
            //Update Stats For Player A
            int rowNoA = sheetApi.getRowNumber(playerA.getCode());
            int rowNoB = sheetApi.getRowNumber(playerB.getCode());
            if (rowNoA < 0 || rowNoB < 0) {
                throw new BeyBladeNotFoundException("Beyblade code not found");
            }

            updateStats(rowNoA,playerA);
            updateStats(rowNoB,playerB);

            if(playerA.getScore() > playerB.getScore()) {
                int win = getCellValue(CellCodes.WIN+rowNoA) + 1;
                sheetApi.updateCell(SHEET_NAME,CellCodes.WIN+rowNoA,win);

                int lose = getCellValue(CellCodes.LOSE+rowNoB) + 1;
                sheetApi.updateCell(SHEET_NAME,CellCodes.LOSE+rowNoB,lose);
            } else {
                int win = getCellValue(CellCodes.WIN+rowNoB) + 1;
                sheetApi.updateCell(SHEET_NAME,CellCodes.WIN+rowNoB,win);

                int lose = getCellValue(CellCodes.LOSE+rowNoA) + 1;
                sheetApi.updateCell(SHEET_NAME,CellCodes.LOSE+rowNoA,lose);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStats(int rowNo,Player player) throws Exception {
        if(player.getSpinFinish() > 0) {
            int spinFinish = player.getSpinFinish() + getCellValue(CellCodes.SPIN_FINISH+rowNo);
            System.out.println("Spin Finish( "+player.getSpinFinish() + " + " + getCellValue(CellCodes.SPIN_FINISH+rowNo)  + ")");
            sheetApi.updateCell(SHEET_NAME,CellCodes.SPIN_FINISH+rowNo,spinFinish);
        }
        if(player.getOverFinish() > 0) {
            int overFinish = player.getOverFinish() + getCellValue(CellCodes.OVER_FINISH+rowNo);
            System.out.println("Over Finish( "+player.getOverFinish() + " + " + getCellValue(CellCodes.OVER_FINISH+rowNo)  + ")");
            sheetApi.updateCell(SHEET_NAME,CellCodes.OVER_FINISH+rowNo,overFinish);
        }
        if(player.getExtremeFinish() > 0) {
            int extremeFinish = player.getExtremeFinish() + getCellValue(CellCodes.EXTREME_FINISH+rowNo);
            System.out.println("Extreme Finish( "+player.getExtremeFinish() + " + " + getCellValue(CellCodes.EXTREME_FINISH+rowNo)  + ")");
            sheetApi.updateCell(SHEET_NAME,CellCodes.EXTREME_FINISH+rowNo,extremeFinish);
        }
        if(player.getBurstFinish() > 0) {
            int burstFinish = player.getBurstFinish() + getCellValue(CellCodes.BURST_FINISH+rowNo);
            System.out.println("Burst Finish( "+player.getBurstFinish() + " + " + getCellValue(CellCodes.BURST_FINISH+rowNo)  + ")" );
            sheetApi.updateCell(SHEET_NAME,CellCodes.BURST_FINISH+rowNo,burstFinish);
        }
    }

    private int getCellValue(String range) {
        int result = 0;
        try {
            String cellValue = sheetApi.getCellValue(range);
            if (cellValue != null) {
                result =  Integer.parseInt(cellValue);
            } else {
                System.out.println("Cell is empty");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
