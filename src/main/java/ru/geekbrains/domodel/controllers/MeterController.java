package ru.geekbrains.domodel.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.domodel.dto.MeterDataDto;
import ru.geekbrains.domodel.dto.MeterDto;
import ru.geekbrains.domodel.entities.constants.Roles;
import ru.geekbrains.domodel.services.api.MeterService;

import java.util.List;


/**
 * Контроллер счетчиков показаний
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/meters")
@Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER})
@RequiredArgsConstructor
public class MeterController {

    private final MeterService meterService;

    @GetMapping("")
    public List<MeterDto> readAllMeters(Authentication authentication) {
        return meterService.getAllMeters(authentication);
    }

    @ApiOperation(value = "Выводит информацию о счетчике по его индексу")
    @GetMapping("/{id}")
    public MeterDto readMeterById(@PathVariable Long id) {
        return meterService.getMeter(id);
    }

    @ApiOperation(value = "Создание счетчика")
    @Secured({Roles.ROLE_ADMIN})
    @PostMapping("")
    public ResponseEntity<MeterDto> createMeter(@RequestBody MeterDto meterDto) {
        MeterDto m = meterService.saveOrUpdate(meterDto);
        if (m == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(m);
    }

    @ApiOperation(value = "Удаление счетчика по его индексу")
    @Secured(Roles.ROLE_ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMeter(@PathVariable Long id) {
        Integer result = meterService.deleteMeterById(id);
        return result > 0 ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    @Secured({Roles.ROLE_ADMIN})
    @PutMapping("")
    public MeterDto updateMeter(@RequestBody MeterDto meterDto) {
        return meterService.saveOrUpdate(meterDto);
    }

    @GetMapping("/{id}/data")
    public List<MeterDataDto> readAllMeterDataByMeterId(@PathVariable Long id) {
        return meterService.getAllMeterDataByMeterId(id);
    }


    @PostMapping("/{id}/data")
    public ResponseEntity<?> createMeterDataByMeterId(@PathVariable Long id, @RequestBody MeterDataDto meterDataDto) {
        MeterDataDto result = meterService.submitMeterData(meterDataDto, id);
        return  result != null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    @ApiOperation(value = "Отдает данные для создания нового счетчика")
    @GetMapping("/add")
    public String getAddPage(Model model, Principal principal) {
        model.addAttribute("accounts", accountService.getAllByUserUsername(principal.getName()));
        model.addAttribute("tariffs", tariffService.getAllTariffs());
        return "meters/meters_add";
    }

    @Secured({Roles.ROLE_ADMIN})
    @DeleteMapping("/data/{dataId}")
    public ResponseEntity<?> deleteMeterDataById(@PathVariable Long dataId) {
        Integer result = meterService.deleteMeterDataById(dataId);
        return result > 0 ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
}
