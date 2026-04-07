package pl.twerdenergoplus.charger.monitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.twerdenergoplus.charger.monitor.dto.RoleCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.RoleDto;
import pl.twerdenergoplus.charger.monitor.entity.Authority;
import pl.twerdenergoplus.charger.monitor.entity.Role;
import pl.twerdenergoplus.charger.monitor.mapper.RoleMapper;
import pl.twerdenergoplus.charger.monitor.repository.AuthorityRepository;
import pl.twerdenergoplus.charger.monitor.repository.RoleRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleMapper roleMapper;

    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .toList();
    }

    public RoleDto findById(Long id) {
        return roleRepository.findById(id)
                .map(roleMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Role not found with id: " + id));
    }

    @Transactional
    public RoleDto create(RoleCreateDto dto) {
        if (roleRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Role already exists: " + dto.getName());
        }
        Role role = new Role();
        role.setName(dto.getName());
        if (dto.getAuthorityIds() != null && !dto.getAuthorityIds().isEmpty()) {
            List<Authority> authorities = authorityRepository.findAllById(dto.getAuthorityIds());
            role.setAuthorities(authorities);
        }
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Transactional
    public RoleDto update(Long id, RoleCreateDto dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Role not found with id: " + id));
        role.setName(dto.getName());
        if (dto.getAuthorityIds() != null) {
            List<Authority> authorities = authorityRepository.findAllById(dto.getAuthorityIds());
            role.setAuthorities(authorities);
        }
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Transactional
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new NoSuchElementException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }
}

