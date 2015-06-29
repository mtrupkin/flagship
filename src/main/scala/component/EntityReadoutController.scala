package component

import javafx.fxml.FXML
import javafx.scene.control.Label

import me.mtrupkin.core.Point
import model.space.Entity

/**
 * Created by mtrupkin on 5/22/2015.
 */
class EntityReadoutController {
  @FXML var position: Label = _
  @FXML var distance: Label = _
  @FXML var typeName: Label = _
  @FXML var id: Label = _
  @FXML var name: Label = _

  implicit def toString(v: core.Vector): String = {
    f"[${v.x}%.2f : ${v.y}%.2f]"
  }

  implicit def toString(p: Point): String = {
    f"[${p.x} : ${p.y}]"
  }

  def update(entity: Entity): Unit = {
    val p = entity.position

    position.setText(p)
    typeName.setText(Entity.typeName(entity))
    id.setText(entity.id)
    name.setText(entity.name)
  }
}
