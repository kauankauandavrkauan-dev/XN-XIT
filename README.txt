XN XIT — projeto Android + servidor de KEY (24 horas)

O que está incluído
- Aplicativo Android nativo em Java: tela XN XIT, entrada de KEY, validação HTTPS e painel inicial.
- Backend PHP + SQLite: validação, painel administrativo, geração de keys de 24h e bloqueio.
- Projeto Android Studio pronto para configurar e compilar.

IMPORTANTE — antes de instalar
Este ambiente não possui Android SDK/Gradle instalado, então não foi possível compilar um APK assinado aqui. Este pacote é o projeto completo para abrir no Android Studio e gerar o APK.

1) Servidor
- Hospede a pasta backend em um servidor com PHP 8+ e extensão PDO_SQLite.
- Configure a variável de ambiente XN_ADMIN_PASSWORD com uma senha forte.
- Se não puder configurar variável de ambiente, altere ADMIN_PASSWORD em backend/config.php antes de publicar.
- Publique somente por HTTPS.
- Abra /backend/admin.php para gerar e bloquear keys.
- A pasta backend precisa ter permissão de escrita para criar keys.sqlite.

2) App
- Abra esta pasta no Android Studio.
- Edite app/src/main/java/com/xnxit/app/MainActivity.java e troque:
  https://SEU-DOMINIO.com/xnxit/api/validate.php
  pela URL HTTPS real do arquivo validate.php.
- Sincronize o Gradle e use Build > Build APK(s).
- O APK de debug ficará em app/build/outputs/apk/debug/app-debug.apk.

3) Segurança
- Troque a senha administrativa padrão antes de publicar.
- Não distribua o app com URL de teste.
- Use HTTPS válido. Não desative a validação de certificado.
- O prazo de 24h é controlado pelo relógio do servidor, não pelo aparelho.

Limites desta versão
- Esta é uma implementação nova e independente, não uma cópia binária modificada do APK enviado.
- Inclui acesso por KEY e painel básico. Recursos que interfiram em partidas, cheats, ESP ou aimbot não fazem parte.
