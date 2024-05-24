import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class FirestoreAccessTest {

    @Mock
    lateinit var auth: FirebaseAuth

    @Mock
    lateinit var firestore: FirebaseFirestore

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testAuthenticatedUserAccess() {
        `when`(auth.currentUser).thenReturn(mock(FirebaseUser::class.java))
        assertTrue(FirestoreAccess(auth, firestore).hasAccess())
    }

    @Test
    fun testUnauthenticatedUserAccess() {
        `when`(auth.currentUser).thenReturn(null)
        assertFalse(FirestoreAccess(auth, firestore).hasAccess())
    }
}

class FirestoreAccess(private val auth: FirebaseAuth, private val firestore: FirebaseFirestore) {
    fun hasAccess(): Boolean = auth.currentUser != null
}
