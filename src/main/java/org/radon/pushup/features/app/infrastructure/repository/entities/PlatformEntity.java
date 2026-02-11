package org.radon.pushup.features.app.infrastructure.repository.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.radon.pushup.features.app.domain.Platform;

import java.util.Set;

@Entity
@Table(name = "platforms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlatformEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Platform platform;
    @ManyToMany(mappedBy = "platform",fetch = FetchType.LAZY)
    private Set<AppEntity> app;

    public PlatformEntity(Platform platform) {
        this.platform = platform;
    }

}
