package pl.twerdenergoplus.charger.monitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.twerdenergoplus.charger.monitor.dto.AuthorityCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.AuthorityDto;
import pl.twerdenergoplus.charger.monitor.entity.Authority;
import pl.twerdenergoplus.charger.monitor.mapper.AuthorityMapper;
import pl.twerdenergoplus.charger.monitor.repository.AuthorityRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

    public List<AuthorityDto> findAll() {
        return authorityRepository.findAll().stream()
                .map(authorityMapper::toDto)
                .toList();
    }

    public AuthorityDto findById(Long id) {
        return authorityRepository.findById(id)
                .map(authorityMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Authority not found with id: " + id));
    }

    @Transactional
    public AuthorityDto create(AuthorityCreateDto dto) {
        if (authorityRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Authority already exists: " + dto.getName());
        }
        Authority entity = authorityMapper.toEntity(dto);
        return authorityMapper.toDto(authorityRepository.save(entity));
    }

    @Transactional
    public AuthorityDto update(Long id, AuthorityCreateDto dto) {
        Authority entity = authorityRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Authority not found with id: " + id));
        authorityMapper.updateEntity(dto, entity);
        return authorityMapper.toDto(authorityRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        if (!authorityRepository.existsById(id)) {
            throw new NoSuchElementException("Authority not found with id: " + id);
        }
        authorityRepository.deleteById(id);
    }
}

