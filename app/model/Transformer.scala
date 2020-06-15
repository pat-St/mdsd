package model

trait Transformer[T1,T2]:
    type Change = Seq[Option[T2]]
    def transform(x: Seq[T1]): Change
  

