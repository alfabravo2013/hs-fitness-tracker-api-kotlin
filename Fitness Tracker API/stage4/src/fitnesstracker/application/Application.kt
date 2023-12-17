package fitnesstracker.application

import fitnesstracker.developer.Developer
import jakarta.persistence.*
import java.time.Instant

@Entity
class Application(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var apiKey: String,

    @ManyToOne
    @JoinColumn(name = "developer_id")
    var developer: Developer,

    var timestamp: Instant = Instant.now()
)
