COMO GERAR O APK DO XN XIT PELO GITHUB ACTIONS (SEM ANDROID STUDIO)

1. Crie uma conta em https://github.com/ (ou entre na sua conta).
2. Clique no + no canto superior direito > New repository.
3. Nome: xn-xit. Marque Public ou Private e clique em Create repository.
4. Dentro do repositório, clique em Add file > Upload files.
5. Abra a pasta do projeto extraído e envie TODO o conteúdo de dentro dela, incluindo a pasta oculta .github.
   Importante: envie os arquivos e pastas, não envie o ZIP dentro do repositório.
6. Clique em Commit changes.
7. Abra a aba Actions do repositório.
8. Se aparecer uma tela pedindo para habilitar Actions, aceite.
9. O fluxo "Build XN XIT APK" deverá iniciar após o commit. Se não iniciar, abra o fluxo e clique em Run workflow.
10. Aguarde ficar verde (Success). Abra a execução concluída, role até Artifacts e baixe "XN-XIT-APK".
11. Extraia o ZIP baixado. Dentro estará app-debug.apk. Envie para o celular e instale.

ATENÇÃO:
- O APK gerado é uma versão de teste/debug, não assinada para distribuição na Play Store.
- O app compila, mas a validação de KEY não funcionará até configurar o backend HTTPS.
- Antes de publicar o backend, troque a senha administrativa em backend/config.php ou configure XN_ADMIN_PASSWORD.
- Edite app/src/main/java/com/xnxit/app/MainActivity.java e substitua https://SEU-DOMINIO.com/xnxit/api/validate.php pelo endereço HTTPS real do backend.
- Depois de alterar o endereço, envie novo commit para disparar outra compilação.
