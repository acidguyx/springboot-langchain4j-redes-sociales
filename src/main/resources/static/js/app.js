// Espera a que el documento esté completamente cargado
document.addEventListener('DOMContentLoaded', () => {
    // Obtiene el formulario de refinamiento
    const refinamientoForm = document.getElementById('refinamientoForm');
    if (refinamientoForm) {
        // Agrega un listener para el evento de submit
        refinamientoForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            // Obtiene los datos del formulario
            const formData = new FormData(refinamientoForm);
            const btnRefinar = document.getElementById('btnRefinar');

            // Valida que se haya seleccionado una idea
            const ideaSeleccionada = formData.get('ideaSeleccionada');
            if (!ideaSeleccionada) {
                mostrarError('Por favor selecciona una idea para personalizar');
                return;
            }

            try {
                // Activa el estado de carga del botón
                toggleLoadingState(btnRefinar, true);

                // Construye el objeto de datos usando los nombres correctos (por ejemplo, "cat_motivacion")
                const requestData = {
                    ideaSeleccionada: ideaSeleccionada,
                    motivacion: formData.get('cat_motivacion'),
                    negocios: formData.get('cat_negocios'),
                    marketing: formData.get('cat_marketing')
                };

                console.log('Enviando datos:', requestData);

                // Envía la petición POST al endpoint '/api/refinar'
                const response = await fetch('/api/refinar', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(requestData)
                });

                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.error || 'Error en la petición');
                }

                // Parsear la respuesta JSON
                const data = await response.json();
                console.log('Respuesta recibida:', data);
                actualizarResultadosRefinamiento(data);

            } catch (error) {
                console.error('Error:', error);
                mostrarError('Ocurrió un error al refinar el contenido: ' + error.message);
            } finally {
                // Desactiva el estado de carga del botón
                toggleLoadingState(btnRefinar, false);
            }
        });
    }
});

/*
 * Función que actualiza el contenedor de resultados con el contenido personalizado.
 * Recorre las categorías y para cada una genera el HTML correspondiente.
 */
function actualizarResultadosRefinamiento(refinamiento) {
    const contenedor = document.getElementById('resultadosRefinamiento');

    // Verifica que la respuesta tenga la estructura esperada
    if (!refinamiento?.respuestasPorCategoria) {
        contenedor.innerHTML = '<div class="mt-8"><p class="text-red-500">Error: Datos de refinamiento no válidos</p></div>';
        return;
    }

    // Comprueba si existe al menos una respuesta en alguna categoría
    const hayRespuestas = Object.values(refinamiento.respuestasPorCategoria)
        .some(categoria => Object.keys(categoria).length > 0);
    if (!hayRespuestas) {
        contenedor.innerHTML = '<div class="mt-8"><p class="text-gray-500">Por favor selecciona al menos una perspectiva para personalizar el contenido.</p></div>';
        return;
    }

    // Comienza a construir el HTML de la sección de contenido personalizado
    let html = `
        <div class="mt-8 space-y-6">
            <h2 class="text-lg font-semibold text-gray-900">Contenido Personalizado</h2>
    `;

    // Recorre cada categoría (ej: cat_motivacion)
    for (const [categoria, respuestas] of Object.entries(refinamiento.respuestasPorCategoria)) {
        if (Object.keys(respuestas).length > 0) {
            // Obtiene el nombre amigable de la categoría desde la configuración global
            const categoriaNombre = getNombreCategoria(categoria);
            html += `
                <div class="bg-white rounded-lg border border-gray-200 overflow-hidden mt-6">
                    <div class="px-4 py-3 bg-gray-50 border-b border-gray-200">
                        <h3 class="text-base font-medium text-gray-900">${categoriaNombre}</h3>
                    </div>
                    <div class="px-4 py-4">
            `;
            // Para cada autor en la categoría, genera el contenido personalizado
            for (const [autorId, dataObj] of Object.entries(respuestas)) {
                const autorNombre = getNombreAutor(autorId);
                html += generarContenidoPersonalidad(autorNombre, dataObj);
            }
            html += '</div></div>';
        }
    }

    html += '</div>';
    contenedor.innerHTML = html;
}

/*
 * Función para obtener el nombre de una categoría usando la configuración global inyectada.
 */
function getNombreCategoria(categoriaId) {
    if (window.configPersonalidades && window.configPersonalidades.categorias) {
        const cat = window.configPersonalidades.categorias.find(c => c.id === categoriaId);
        return cat ? cat.nombre : categoriaId;
    }
    return categoriaId;
}

/*
 * Función para obtener el nombre de un autor usando la configuración global inyectada.
 */
function getNombreAutor(autorId) {
    if (window.configPersonalidades && window.configPersonalidades.categorias) {
        for (const categoria of window.configPersonalidades.categorias) {
            const autor = categoria.autores.find(a => a.id === autorId);
            if (autor) return autor.nombre;
        }
    }
    return autorId;
}

/*
 * Genera el HTML para el bloque de contenido personalizado de un autor.
 * Muestra la respuesta general y, si existe, el contenido por red social.
 */
function generarContenidoPersonalidad(autorNombre, dataObj) {
    return `
        <div class="mb-8">
            <div class="flex items-center mb-4">
                <span class="inline-flex items-center justify-center h-10 w-10 rounded-full bg-indigo-100">
                    <span class="text-lg font-medium text-indigo-800">${autorNombre.charAt(0)}</span>
                </span>
                <h4 class="ml-3 text-lg font-medium text-gray-900">${autorNombre}</h4>
            </div>
            
            <!-- Muestra la respuesta general del autor -->
            <div class="mb-6 p-4 bg-gray-50 rounded-lg">
                <p class="text-gray-700 whitespace-pre-line">${dataObj.respuesta}</p>
            </div>
            
            <!-- Si existe contenido por red social, se genera su sección -->
            ${ (dataObj.contenidoRedes && dataObj.contenidoRedes.length > 0) ? `
                <div class="mt-4">
                    <h5 class="text-base font-semibold text-gray-900 mb-2">Contenido por Red Social</h5>
                    ${generarContenidoRedes(dataObj.contenidoRedes)}
                </div>
            ` : '' }
        </div>
    `;
}

/*
 * Genera el HTML para cada entrada de contenido de una red social.
 */
function generarContenidoRedes(contenidoRedes) {
    return contenidoRedes.map(red => `
        <div class="mb-6 border border-gray-200 rounded-lg">
            <div class="px-4 py-3 bg-gray-50 border-b border-gray-200">
                <h5 class="font-medium text-gray-900">${red.redSocial}</h5>
            </div>
            <div class="p-4 space-y-4">
                <!-- Muestra el contenido principal para la red social -->
                <div class="bg-white p-4 rounded-lg border border-gray-200">
                    <p class="text-gray-600 whitespace-pre-line">${red.contenido}</p>
                </div>
                <!-- Se generan secciones para hashtags, consejos y extras -->
                ${generarSeccionHashtags(red.hashtags)}
                ${generarSeccionConsejos(red.consejos)}
                ${generarSeccionExtras(red.extras)}
            </div>
        </div>
    `).join('');
}

/*
 * Genera el HTML para mostrar los hashtags asociados.
 */
function generarSeccionHashtags(hashtags) {
    if (!hashtags || hashtags.length === 0) return '';
    return `
        <div>
            <h6 class="font-medium text-sm text-gray-700 mb-2">Hashtags</h6>
            <div class="flex flex-wrap gap-2">
                ${hashtags.map(hashtag => `
                    <span class="inline-flex items-center px-3 py-1 rounded-full text-sm bg-blue-100 text-blue-800">
                        ${hashtag}
                    </span>
                `).join('')}
            </div>
        </div>
    `;
}

/*
 * Genera el HTML para mostrar los consejos de publicación.
 */
function generarSeccionConsejos(consejos) {
    if (!consejos || consejos.length === 0) return '';
    return `
        <div class="bg-yellow-50 p-4 rounded-lg">
            <h6 class="font-medium text-sm text-yellow-800 mb-2">Consejos de Publicación</h6>
            <ul class="list-disc pl-4 space-y-1">
                ${consejos.map(consejo => `
                    <li class="text-yellow-700">${consejo}</li>
                `).join('')}
            </ul>
        </div>
    `;
}

/*
 * Genera el HTML para mostrar los detalles adicionales (extras).
 */
function generarSeccionExtras(extras) {
    if (!extras) return '';
    return `
        <div class="bg-indigo-50 p-4 rounded-lg">
            <h6 class="font-medium text-sm text-indigo-800 mb-2">Detalles Adicionales</h6>
            <div class="space-y-2">
                <p class="text-sm text-indigo-700">
                    <span class="font-medium">Tipo de Contenido:</span> ${extras.tipo_contenido}
                </p>
                <p class="text-sm text-indigo-700">
                    <span class="font-medium">Elementos Visuales:</span> ${extras.elementos_visuales}
                </p>
                <p class="text-sm text-indigo-700">
                    <span class="font-medium">Estrategia:</span> ${extras.estrategia}
                </p>
            </div>
        </div>
    `;
}

/*
 * Cambia el estado de carga del botón (habilita/deshabilita y modifica estilos).
 */
function toggleLoadingState(button, isLoading) {
    const normalState = button.querySelector('.normal-state');
    const loadingState = button.querySelector('.loading-state');
    button.disabled = isLoading;
    button.classList.toggle('opacity-75', isLoading);
    button.classList.toggle('cursor-not-allowed', isLoading);
    normalState.classList.toggle('hidden', isLoading);
    loadingState.classList.toggle('hidden', !isLoading);
}

/*
 * Muestra un mensaje de error en una alerta temporal.
 */
function mostrarError(mensaje) {
    const errorDiv = document.createElement('div');
    errorDiv.className = 'fixed top-4 right-4 bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded z-50';
    errorDiv.innerHTML = mensaje;
    document.body.appendChild(errorDiv);
    setTimeout(() => { errorDiv.remove(); }, 5000);
}
