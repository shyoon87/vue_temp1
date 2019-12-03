import 'url-search-params-polyfill'

export function post_json(json) {
  const params = new URLSearchParams()

  Object.keys(json).forEach(key => {
    params.append(key, json[key])
  })
  return params
}
