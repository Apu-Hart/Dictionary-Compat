$ErrorActionPreference = 'Stop'
$lab = Split-Path $PSScriptRoot
$project = Join-Path (Split-Path $lab) 'questgames-release-20261021\questgames'
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21.0.11'
if (!(Test-Path -LiteralPath "$project\build\moddev\clientLegacyClasspath.txt")) { throw 'Missing reviewed NeoForge development classpath; generate the Quest Games development dependencies first' }
& "$project\gradlew.bat" -p "$lab\source\dictionarycompat" "-PquestProject=$project" --gradle-user-home "$project\.gradle-isolated" --offline --no-daemon --console=plain clean build
if ($LASTEXITCODE) { throw 'Candidate build failed' }
$built = "$lab\source\dictionarycompat\build\libs\dictionarycompat-0.1.0-candidate.1.jar"
$candidate = "$lab\patches\dictionarycompat-0.1.0-candidate.1.jar"
if (Test-Path -LiteralPath $candidate) {
    if ((Get-FileHash -LiteralPath $built -Algorithm SHA256).Hash -ne (Get-FileHash -LiteralPath $candidate -Algorithm SHA256).Hash) { throw 'Candidate differs: do not overwrite the tested artifact; review changes and assign a new candidate version' }
} else {
    Copy-Item -LiteralPath $built -Destination $candidate
}
Get-FileHash -LiteralPath $candidate -Algorithm SHA256
