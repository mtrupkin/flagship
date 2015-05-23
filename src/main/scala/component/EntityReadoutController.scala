package component

import javafx.fxml.FXML
import javafx.scene.control.Label

import model.Entity

/**
 * Created by mtrupkin on 5/22/2015.
 */
class EntityReadoutController {
  @FXML var position: Label = _
  @FXML var distance: Label = _
  @FXML var typeName: Label = _
  @FXML var id: Label = _
  @FXML var name: Label = _

  def update(entity: Entity): Unit = {
    val p = entity.position

    position.setText(s"[${p.x}:${p.y}]")
    typeName.setText(entity.typeName)
    id.setText(entity.id)
    name.setText(entity.name)
  }
}
