package dynamics

/*
 * Created by mtrupkin on 7/4/2015.
 */
case class Dynamics(x: core.Vector, v: core.Vector)
case class Derivative(dx: core.Vector, dv: core.Vector)

object Dynamics {
  def acceleration(state: Dynamics, t: Double): core.Vector = core.Vector(10, 0)

  def evaluate(initial: Dynamics, t: Double): Derivative = Derivative(initial.v, acceleration(initial, t))
  def evaluate(initial: Dynamics, t: Double, dt: Double, d: Derivative) = {
    val state = Dynamics(initial.x + d.dx*dt, initial.v + d.dv*dt)
    Derivative(state.v, acceleration(state, t+dt))
  }

  def integrate(state: Dynamics, t: Double, dt: Double): Dynamics = {
    val a = evaluate(state, t)
    val b = evaluate(state, t, dt*0.5, a)
    val c = evaluate(state, t, dt*0.5, b)
    val d = evaluate(state, t, dt, c)


    val dxdt = (a.dx + (b.dx + c.dx) * 2.0 + d.dx) * 1.0/6.0
    val dvdt = (a.dv + (b.dv + c.dv) * 2.0 + d.dv) * 1.0/6.0

    Dynamics(state.x + dxdt*dt, state.v + dvdt*dt)
  }
}
