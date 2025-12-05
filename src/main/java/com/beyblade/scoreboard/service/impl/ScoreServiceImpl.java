package com.beyblade.scoreboard.service.impl;

import com.beyblade.scoreboard.constant.CellCodes;
import com.beyblade.scoreboard.dto.Beyblade;
import com.beyblade.scoreboard.dto.Player;
import com.beyblade.scoreboard.exception.BeyBladeNotFoundException;
import com.beyblade.scoreboard.exception.ScoreCannotBeTiedException;
import com.beyblade.scoreboard.service.ScoreService;
import com.beyblade.scoreboard.middleware.GoogleSheetApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

            if (playerA.getScore() == playerB.getScore()) {
                throw new ScoreCannotBeTiedException("Score cannot be tied");
            }

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
        } catch (BeyBladeNotFoundException bnfe) {
            throw new BeyBladeNotFoundException("Beyblade code not found");
        } catch (ScoreCannotBeTiedException scbte) {
            throw new ScoreCannotBeTiedException("Score Cannot be Tied");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Beyblade> getBeyblades() {
        try {
            List<Beyblade> beyblades = convertToBeybladeList(sheetApi.readData("Sheet1!A1:K"));
            List<Beyblade> ranked = rankByWinRateAndWins(beyblades);
            ranked.sort(Comparator.comparingInt(Beyblade::getRank));
            return ranked;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Beyblade> convertToBeybladeList(List<List<Object>> rows) {
        List<Beyblade> list = new ArrayList<>();

        for (int i = 1; i < rows.size(); i++) {
            List<Object> row = rows.get(i);
            Beyblade b = new Beyblade();
            b.setCode(getString(row, CellCodes.CODE_index));
            b.setName(getString(row, CellCodes.BEYBLADE_NAME_index));
            b.setMatches(getInt(row, CellCodes.MATCHES_index));
            b.setWin(getInt(row, CellCodes.WIN_index));
            b.setLose(getInt(row, CellCodes.LOSE_index));
            b.setWinRate(getString(row, CellCodes.WIN_RATE_index));
            b.setLoseRate(getString(row, CellCodes.LOSE_RATE_index));
            b.setBurst(getInt(row, CellCodes.BURST_FINISH_index));
            b.setSpin(getInt(row, CellCodes.SPIN_FINISH_index));
            b.setExtreme(getInt(row, CellCodes.EXTREME_FINISH_index));
            b.setOverfinish(getInt(row, CellCodes.OVER_FINISH_index));
            list.add(b);
        }

        return list;
    }

    private String getString(List<Object> row, int index) {
        if (index >= row.size()) return "";
        return String.valueOf(row.get(index));
    }

    private int getInt(List<Object> row, int index) {
        if (index >= row.size()) return 0;
        try {
            return Integer.parseInt(row.get(index).toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public List<Beyblade> rankByWinRateAndWins(List<Beyblade> list) {
        list.sort((a, b) -> {
            int cmp = Double.compare(
                    parseWinRate(b.getWinRate()),
                    parseWinRate(a.getWinRate())
            );

            if (cmp != 0) return cmp;

            return Integer.compare(b.getWin(), a.getWin());
        });

        int rank = 1;
        int skipCount = 1;

        list.get(0).setRank(rank);

        for (int i = 1; i < list.size(); i++) {
            Beyblade prev = list.get(i - 1);
            Beyblade curr = list.get(i);

            double prevRate = parseWinRate(prev.getWinRate());
            double currRate = parseWinRate(curr.getWinRate());

            boolean sameRate = Double.compare(prevRate, currRate) == 0;
            boolean sameWins = prev.getWin() == curr.getWin();

            if (sameRate && sameWins) {
                curr.setRank(rank);
                skipCount++;
            } else {
                rank += skipCount;
                curr.setRank(rank);
                skipCount = 1;
            }
        }

        return list;
    }

    private double parseWinRate(String value) {
        if (value == null || value.isBlank()) return 0.0;

        value = value.trim();

        if (value.endsWith("%")) {
            try {
                return Double.parseDouble(value.replace("%", "").trim());
            } catch (Exception ignored) {
                return 0.0;
            }
        }

        try {
            double num = Double.parseDouble(value);
            if (num <= 1) return num * 100; // e.g. 0.75 → 75
            return num;
        } catch (Exception e) {
            return 0.0;
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
