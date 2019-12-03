const getters = {
  sidebar: state => state.app.sidebar,
  language: state => state.app.language,
  size: state => state.app.size,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  id: state => state.user.id,
  name: state => state.user.name,
  introduction: state => state.user.introduction,
  status: state => state.user.status,
  role: state => state.user.role,
  setting: state => state.user.setting,
  routes: state => state.user.routes
}
export default getters
