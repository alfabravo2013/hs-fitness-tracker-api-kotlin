package fitnesstracker.developer

import jakarta.persistence.*

@Entity
class Developer(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    @Column(unique = true, nullable = false)
    var email: String,

    @Column(nullable = false)
    var password: String
)
