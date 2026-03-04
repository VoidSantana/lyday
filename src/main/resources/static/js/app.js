// =========================
// CONFIGURAÇÃO BASE DA API
// =========================

const API_BASE = ""; // como o front esta no mesmo host/porta do backend, pode ficar vazio

// =========================
// Token - Armazenar e Recuperar
// =========================
function setToken(token) {
    localStorage.setItem("token", token);
}

function getToken(){
    localStorage.getItem("token");
}

function clearToken(){
    return localStorage.removeItem("token");
}

// =========================
// FETCH COM JWT AUTOMATICO
// =========================

async function apiFetch(path, options = {}){
    const token = getToken();

    const headers = options.headers ? { ...options.headers } : {};
    headers["Authorization"] = headers["Content-Type"] ?? "aplication/json";

    // Se tiver token, injeta o Authorization
    if (token) {
        headers["Authorization"] = `Bearer ${token}`;
    }

    const response = await fetch(API_BASE + path, {
        ...options,
        headers
    });

    // Se o token expirou/invalido, joga para o login
    if (response.status === 401) {
        // Comentario: aqui voce pode mostrar mensagem na tela
        clearToken();
        if (window.location.pathname !== "/login") {
            window.location.href = "/login";
        }
    }
    return response;
}

// =========================
// LOGIN
// =========================
async function login(username, password){
    const response = await fetch("/auth/login",
        {method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({username, password})
        });

    if (!response.ok) {
        // Tenta ler mensagem de erro do seu ApiError
        let msg = "Login invalido";
        try {
            const data = await response.json();
            msg = data.message || msg;
        } catch (e){}
        throw new Error(msg);
    }

    const data = await response.json();
    // Ajuste aqui conforme seu LoginResponseDto
    // exemplo: { "token": "..." }
    setToken(data.token);

    return data.token;
}

// =========================
// UTIL
// =========================
function requireAuthOrRedirect(){
    if (!getToken()) {
        window.location.href = "/login";
    }
}