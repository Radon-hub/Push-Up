package org.radon.pushup.features.app.infrastructure.repository.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.radon.pushup.features.app.domain.ApiKeyStatus;

import java.sql.Timestamp;


@Entity
@Table(name = "api_keys")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String api_key;
    private String api_key_prefix;
    @Enumerated(EnumType.STRING)
    private ApiKeyStatus api_key_status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_id")
    private AppEntity app;
    private Timestamp created_at = new Timestamp(System.currentTimeMillis());
    private Timestamp updated_at = new Timestamp(System.currentTimeMillis());

    public ApiKeyEntity(String api_key, String api_key_prefix, ApiKeyStatus api_key_status, AppEntity app) {
        this.api_key = api_key;
        this.api_key_prefix = api_key_prefix;
        this.api_key_status = api_key_status;
        this.app = app;
    }

}
